package com.diseaseexpertsystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.diseaseexpertsystem.engine.DiagnosisResult;
import com.diseaseexpertsystem.engine.InferenceEngine;
import com.diseaseexpertsystem.knowledge.KnowledgeBase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MainController {
  @FXML
  private VBox symptomContainer;

  @FXML
  private TextArea resultDisplay;

  @FXML
  private TextField threshold;

  /* custom variable */

  private KnowledgeBase diseaseKnowledgeBase = new KnowledgeBase();
  private InferenceEngine engine = new InferenceEngine(diseaseKnowledgeBase);
  private Map<String, CheckBox> checkBoxesMap = new HashMap<>();

  /* lifecycle handler */

  @FXML
  private void initialize() {
    renderCheckBoxes();
    threshold.setText("0");
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
    if (!validateThreshold())
      return;

    if (checkBoxesMap.values().stream().noneMatch(CheckBox::isSelected)) {
      resultDisplay.setText("Pilih minimal satu gejala.");
      return;
    }

    Map<String, Boolean> userAnswers = new HashMap<>();
    checkBoxesMap.forEach((name, cb) -> userAnswers.put(name, cb.isSelected()));

    // get the list of categories under Root
    List<DiagnosisResult> categories = engine.getResultsAtLevel("Root", userAnswers);
    DiagnosisResult topCategory = categories.get(0);

    // get the list of diseases under the top category
    List<DiagnosisResult> diseases = engine.getResultsAtLevel(topCategory.name, userAnswers);

    // build the result string
    StringBuilder sb = new StringBuilder();

    sb.append("Diagnosis Kategori Penyakit:\n");
    sb.append(topCategory.name).append("\n\n");
    sb.append("Diagnosis Penyakit:\n");

    double thresholdVal = Double.parseDouble(threshold.getText());
    int rank = 1;
    boolean found = false;

    for (DiagnosisResult d : diseases) {
      if (d.percentage >= thresholdVal) {
        sb.append(rank++).append(". ").append(d.name)
            .append(" (").append(String.format("%.1f", d.percentage)).append("%)\n");
        found = true;
      }
    }

    if (!found)
      sb.append("Tidak ada penyakit yang melewati threshold.\n");

    sb.append("\nThreshold: ").append(thresholdVal).append("%");
    resultDisplay.setText(sb.toString());
  }

  @FXML
  void resetSymptom(ActionEvent event) {
    for (CheckBox cb : checkBoxesMap.values()) {
      cb.setSelected(false);
    }

    resultDisplay.clear();
  }

  /* custom method */

  private boolean validateThreshold() {
    String input = threshold.getText();

    if (input.isEmpty()) {
      resultDisplay.setText("Threshold tidak boleh kosong.");
      return false;
    }

    double value = Double.parseDouble(input);

    if (value < 0 || value > 100) {
      resultDisplay.setText("Threshold harus antara 0 dan 100.");
      return false;
    }

    return true;
  }
}
