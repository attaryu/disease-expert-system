package com.diseaseexpertsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MainController {
  @FXML
  private VBox symptomContainer;

  @FXML
  void diagnoseDisease(ActionEvent event) {
    System.out.println("Diagnosing disease...");
  }

  @FXML
  void resetSymptom(ActionEvent event) {
    System.out.println("Resetting symptoms...");
  }
}
