package com.diseaseexpertsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.diseaseexpertsystem.knowledge.KnowledgeBase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class MainController {
  @FXML
  private VBox symptomContainer;

  @FXML
  private TextArea resultDisplay;

  private KnowledgeBase diseaseKnowledgeBase = new KnowledgeBase();
  private Map<String, CheckBox> checkBoxesMap = new HashMap<>();

  /* lifecycle handler */

  @FXML
  private void initialize() {
    renderCheckBoxes();
  }

  private void renderCheckBoxes() {
    Set<String> symptoms = diseaseKnowledgeBase.getUniqueLeaf();

    for (String symptom : symptoms) {
      CheckBox cb = new CheckBox(symptom);

      checkBoxesMap.put(symptom, cb);
      symptomContainer.getChildren().add(cb);
    }
  }

  /* interaction handler */

  @FXML
  void diagnoseDisease(ActionEvent event) {
    StringBuilder sb = new StringBuilder();
    sb.append("Gejala yang dipilih:\n");

    for (Map.Entry<String, CheckBox> entry : checkBoxesMap.entrySet()) {
      if (entry.getValue().isSelected()) {
        sb.append("- ").append(entry.getKey()).append("\n");
      }
    }

    resultDisplay.setText(sb.toString());
  }

  @FXML
  void resetSymptom(ActionEvent event) {
    for (CheckBox cb : checkBoxesMap.values()) {
      cb.setSelected(false);
    }

    resultDisplay.clear();
  }
}
