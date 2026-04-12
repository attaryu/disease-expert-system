package com.diseaseexpertsystem.knowledge;

public class Evidence {
  private String name;
  private double weight;

  public Evidence(String name, double weight) {
    this.name = name;
    this.weight = weight;
  }

  public String getName() {
    return name;
  }

  public double getWeight() {
    return weight;
  }
}