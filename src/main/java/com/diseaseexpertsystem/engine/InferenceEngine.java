package com.diseaseexpertsystem.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.diseaseexpertsystem.knowledge.DiseaseKnowledgeBaseAbstract;
import com.diseaseexpertsystem.knowledge.Evidence;

public class InferenceEngine {
  private DiseaseKnowledgeBaseAbstract kb;

  public InferenceEngine(DiseaseKnowledgeBaseAbstract kb) {
    this.kb = kb;
  }

  public void setKnowledgeBase(DiseaseKnowledgeBaseAbstract kb) {
    this.kb = kb;
  }

  public DiseaseKnowledgeBaseAbstract getKnowledgeBase() {
    return kb;
  }

  // Metode diubah dari Recursive menjadi Iterative menggunakan Stack
  public double calculateNode(String targetNode, Map<String, Boolean> userAnswers) {
    Map<String, List<Evidence>> graph = kb.getKnowledge();

    // Map untuk menyimpan nilai node yang sudah dihitung agar tidak diulang
    // (Memoisasi)
    Map<String, Double> computedScores = new HashMap<>();

    // Stack untuk melacak urutan node yang perlu diproses
    Stack<String> stack = new Stack<>();
    stack.push(targetNode);

    while (!stack.isEmpty()) {
      String current = stack.peek();

      // Jika node sudah dihitung sebelumnya, abaikan dan keluarkan dari stack
      if (computedScores.containsKey(current)) {
        stack.pop();
        continue;
      }

      // Base Case: Jika node tidak ada di graph, berarti ini LEAF (Gejala Fisik)
      if (!graph.containsKey(current)) {
        double score = userAnswers.getOrDefault(current, false) ? 1.0 : 0.0;

        computedScores.put(current, score);
        stack.pop();

        continue;
      }

      // Iterative Case: Node adalah PARENT. Cek apakah semua child sudah dihitung.
      List<Evidence> children = graph.get(current);
      boolean allChildrenComputed = true;

      for (Evidence child : children) {
        if (!computedScores.containsKey(child.getName())) {
          stack.push(child.getName()); // Masukkan child ke stack untuk dievaluasi
          allChildrenComputed = false;
        }
      }

      // Jika semua child sudah dievaluasi, saatnya kalkulasi nilai parent ini
      if (allChildrenComputed) {
        double totalScore = 0.0;

        for (Evidence child : children) {
          double childScore = computedScores.get(child.getName());

          // Kalkulasi matematika sesuai rule Anda:
          // Contoh: Jika nilai childScore adalah 0.75 (75%) dan bobot child ke parent
          // adalah 0.20 (20%)
          // Maka hasil kalinya adalah 0.15 (15%) yang akan disumbangkan ke parent
          totalScore += (childScore * child.getWeight());
        }

        computedScores.put(current, totalScore);
        stack.pop(); // Parent selesai dihitung, keluarkan dari stack
      }
    }

    // Kembalikan nilai akhir untuk node yang dituju
    return computedScores.getOrDefault(targetNode, 0.0);
  }

  public List<DiagnosisResult> getResultsAtLevel(String parentName, Map<String, Boolean> userAnswers) {
    List<DiagnosisResult> results = new ArrayList<>();
    List<Evidence> children = kb.getKnowledge().get(parentName);

    if (children != null) {
      for (Evidence child : children) {
        double score = calculateNode(child.getName(), userAnswers);
        results.add(new DiagnosisResult(child.getName(), score * 100)); // Dikalikan 100 agar jadi persentase bulat
      }
    }

    // Urutkan dari persentase tertinggi
    results.sort((a, b) -> Double.compare(b.percentage, a.percentage));
    return results;
  }
}