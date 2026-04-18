package com.diseaseexpertsystem.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.diseaseexpertsystem.knowledge.DiseaseKnowledgeBaseAbstract;
import com.diseaseexpertsystem.knowledge.Rule;

public class RuleBasedInferenceEngine implements InferenceEngineInterface {
  private DiseaseKnowledgeBaseAbstract kb;

  public RuleBasedInferenceEngine(DiseaseKnowledgeBaseAbstract kb) {
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

    for (Rule rule : kb.getRules()) {
      List<String> requiredSymptoms = rule.getSymptoms();
      boolean ruleMatched = true;

      if (requiredSymptoms.isEmpty()) {
        continue;
      }

      for (String symptomCode : requiredSymptoms) {
        if (!userAnswers.getOrDefault(symptomCode, false)) {
          ruleMatched = false;
          break;
        }
      }

      if (ruleMatched) {
        results.add(new DiagnosisResult(rule.getDiseaseName(), 100.0));
      }
    }

    return results;
  }
}