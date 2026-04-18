package com.diseaseexpertsystem.engine;

import java.util.List;
import java.util.Map;

import com.diseaseexpertsystem.knowledge.DiseaseKnowledgeBaseAbstract;

public interface InferenceEngineInterface {
  void setKnowledgeBase(DiseaseKnowledgeBaseAbstract kb);

  DiseaseKnowledgeBaseAbstract getKnowledgeBase();

  List<DiagnosisResult> diagnose(Map<String, Boolean> userAnswers);
}
