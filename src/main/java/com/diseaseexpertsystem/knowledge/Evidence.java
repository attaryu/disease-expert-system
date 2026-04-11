package com.diseaseexpertsystem.knowledge;

public class Evidence {
  String name;
  double weight;

  Evidence(String name, double weight) {
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