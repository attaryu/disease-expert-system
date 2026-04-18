package com.diseaseexpertsystem.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.diseaseexpertsystem.knowledge.DiseaseKnowledgeBaseAbstract;
import com.diseaseexpertsystem.knowledge.Evidence;

public class WeightedInferenceEngine implements InferenceEngineInterface {
  private DiseaseKnowledgeBaseAbstract kb;

  public WeightedInferenceEngine(DiseaseKnowledgeBaseAbstract kb) {
    this.kb = kb;
  }

  @Override
  public void setKnowledgeBase(DiseaseKnowledgeBaseAbstract kb) {
    this.kb = kb;
  }

  @Override
  public DiseaseKnowledgeBaseAbstract getKnowledgeBase() {
    return kb;
  }

  @Override
  public List<DiagnosisResult> diagnose(Map<String, Boolean> userAnswers) {
    List<DiagnosisResult> results = new ArrayList<>();

    // Weighted logic sebelumnya hardcode mengevaluasi "Root"
    List<Evidence> children = kb.getKnowledge().get("Root");

    if (children != null) {
      for (Evidence child : children) {
        double score = calculateNode(child.getName(), userAnswers);
        results.add(new DiagnosisResult(child.getName(), score * 100));
      }
    }

    results.sort((a, b) -> Double.compare(b.percentage, a.percentage));
    return results;
  }

  private double calculateNode(String targetNode, Map<String, Boolean> userAnswers) {
    Map<String, List<Evidence>> graph = kb.getKnowledge();

    Map<String, Double> computedScores = new HashMap<>();
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
          stack.push(child.getName());
          allChildrenComputed = false;
        }
      }

      if (allChildrenComputed) {
        double totalScore = 0.0;

        for (Evidence child : children) {
          double childScore = computedScores.get(child.getName());
          totalScore += (childScore * child.getWeight());
        }

        computedScores.put(current, totalScore);
        stack.pop();
      }
    }

    return computedScores.getOrDefault(targetNode, 0.0);
  }
}