package com.diseaseexpertsystem.knowledge;

import java.util.List;

public class Rule {
  private String diseaseName;
  private List<String> symptoms;

  public Rule(String diseaseName, List<String> symptoms) {
    this.diseaseName = diseaseName;
    this.symptoms = symptoms;
  }

  public String getDiseaseName() {
    return diseaseName;
  }

  public List<String> getSymptoms() {
    return symptoms;
  }
}