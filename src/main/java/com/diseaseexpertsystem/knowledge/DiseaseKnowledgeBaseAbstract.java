package com.diseaseexpertsystem.knowledge;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class DiseaseKnowledgeBaseAbstract {
  protected Map<String, String> masterSymptoms = new LinkedHashMap<>();
  protected Map<String, List<Evidence>> knowledgeGraph = new LinkedHashMap<>();
  protected List<Rule> rules = new ArrayList<>();

  public abstract Map<String, List<Evidence>> getKnowledge();

  public abstract Map<String, String> getSymptoms();

  public abstract List<Rule> getRules();
}
