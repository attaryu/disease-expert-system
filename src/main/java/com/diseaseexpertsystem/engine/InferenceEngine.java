package com.diseaseexpertsystem.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  public double calculateNode(String nodeName, Map<String, Boolean> userAnswers) {
    Map<String, List<Evidence>> graph = kb.getKnowledge();

    // Base Case: Jika node adalah LEAF (Gejala Fisik)
    if (!graph.containsKey(nodeName)) {
      return userAnswers.getOrDefault(nodeName, false) ? 1.0 : 0.0;
    }

    // Recursive Case: Jika node adalah PARENT (Penyakit/Kategori)
    double totalScore = 0.0;
    List<Evidence> children = graph.get(nodeName);

    for (Evidence child : children) {
      double childScore = calculateNode(child.getName(), userAnswers);
      totalScore += (childScore * child.getWeight());
    }

    return totalScore;
  }

  public List<DiagnosisResult> getResultsAtLevel(String parentName, Map<String, Boolean> userAnswers) {
    List<DiagnosisResult> results = new ArrayList<>();
    List<Evidence> children = kb.getKnowledge().get(parentName);

    if (children != null) {
      for (Evidence child : children) {
        double score = calculateNode(child.getName(), userAnswers);
        results.add(new DiagnosisResult(child.getName(), score * 100));
      }
    }
    // Urutkan dari persentase tertinggi
    results.sort((a, b) -> Double.compare(b.percentage, a.percentage));
    return results;
  }
}
