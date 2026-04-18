package com.diseaseexpertsystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.diseaseexpertsystem.engine.DiagnosisResult;
import com.diseaseexpertsystem.engine.InferenceEngineInterface;
import com.diseaseexpertsystem.engine.RuleBasedInferenceEngine;
import com.diseaseexpertsystem.engine.WeightedInferenceEngine;
import com.diseaseexpertsystem.knowledge.DiseaseKnowledgeBaseAbstract;
import com.diseaseexpertsystem.knowledge.knowledges.GastroenteritisKnowledgeBase;
import com.diseaseexpertsystem.knowledge.knowledges.InfectionKnowledgeBase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class MainController {
  @FXML
  private ToggleGroup diseaseGroup;

  @FXML
  private ToggleGroup method;

  @FXML
  private VBox symptomContainer;

  @FXML
  private TextArea resultDisplay;

  @FXML
  private TextField threshold;

  @FXML
  private Label thresholdLabel;

  @FXML
  private Label thresholdPercentage;

  /* custom variable */

  private DiseaseKnowledgeBaseAbstract knowledgeBase;
  private InferenceEngineInterface engine;
  private Map<String, CheckBox> checkBoxesMap = new HashMap<>();

  /* lifecycle handler */

  @FXML
  private void initialize() {
    knowledgeBase = new InfectionKnowledgeBase();
    engine = new WeightedInferenceEngine(knowledgeBase);

    renderCheckBoxes();
    listenToogleGroup();
    threshold.setText("0");
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
    for (Map.Entry<String, CheckBox> entry : checkBoxesMap.entrySet()) {
      userAnswers.put(entry.getKey(), entry.getValue().isSelected());
    }

    List<DiagnosisResult> results = engine.diagnose(userAnswers);

    RadioButton selectedMethodRb = (RadioButton) method.getSelectedToggle();
    boolean isRuleBased = selectedMethodRb != null && selectedMethodRb.getText().equals("Rule-based");
    String methodName = selectedMethodRb != null ? selectedMethodRb.getText() : "Unknown";

    StringBuilder aboveThreshold = new StringBuilder(String.format("Metode: %s\n", methodName));

    if (!isRuleBased) {
      aboveThreshold.append("Memenuhi Threshold\n");
    } else {
      aboveThreshold.append("Kecocokan Gejala (Rules Matched):\n");
    }

    StringBuilder belowThreshold = new StringBuilder(String.format("\nDi Bawah Threshold\n"));

    boolean hasAbove = false;
    boolean hasBelow = false;

    double thresholdValue = isRuleBased ? 0.0 : Double.parseDouble(threshold.getText());

    for (DiagnosisResult res : results) {
      if (isRuleBased) {
        aboveThreshold.append(String.format("- %s\n", res.name));
        hasAbove = true;
      } else {
        String formattedResult = String.format("- %s: %.2f%%\n", res.name, res.percentage);

        if (res.percentage >= thresholdValue) {
          aboveThreshold.append(formattedResult);
          hasAbove = true;
        } else {
          belowThreshold.append(formattedResult);
          hasBelow = true;
        }
      }
    }

    if (!hasAbove)
      aboveThreshold.append("- Tidak ada\n");
    if (!hasBelow && !isRuleBased)
      belowThreshold.append("- Tidak ada\n");

    if (isRuleBased) {
      resultDisplay.setText(aboveThreshold.toString());
    } else {
      resultDisplay.setText(String.format("Threshold: %.2f%%\n", thresholdValue) + aboveThreshold.toString()
          + belowThreshold.toString());
    }
  }

  @FXML
  void resetSymptom(ActionEvent event) {
    for (CheckBox cb : checkBoxesMap.values()) {
      cb.setSelected(false);
    }

    resultDisplay.clear();
  }

  /* custom method */

  private void listenToogleGroup() {
    // Listener untuk pergantian Penyakit (Knowledge Base)
    diseaseGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
      if (newToggle == null) {
        symptomContainer.getChildren().clear();
        checkBoxesMap.clear();
        return;
      }

      updateEngineStrategy();

      symptomContainer.getChildren().clear();
      checkBoxesMap.clear();
      resultDisplay.clear();
      renderCheckBoxes();
    });

    // Listener untuk pergantian Metode (Weighted vs Rule-based)
    method.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
      if (newToggle != null) {
        updateEngineStrategy();
      }
    });
  }

  private void updateEngineStrategy() {
    RadioButton selectedDiseaseRb = (RadioButton) diseaseGroup.getSelectedToggle();
    RadioButton selectedMethodRb = (RadioButton) method.getSelectedToggle();

    if (selectedDiseaseRb == null || selectedMethodRb == null)
      return;

    String selectedDisease = selectedDiseaseRb.getText();
    String selectedMethod = selectedMethodRb.getText();

    DiseaseKnowledgeBaseAbstract activeKb = selectedDisease.equals("Infeksi/Non-infeksi")
        ? new InfectionKnowledgeBase()
        : new GastroenteritisKnowledgeBase();

    if (selectedMethod.equals("Weighted")) {
      toggleThresholdUI(true);
      engine = new WeightedInferenceEngine(activeKb);
    } else {
      toggleThresholdUI(false);
      engine = new RuleBasedInferenceEngine(activeKb);
    }
  }

  private void renderCheckBoxes() {
    Map<String, String> symptoms = engine.getKnowledgeBase().getSymptoms();

    for (String symptom : symptoms.keySet()) {
      CheckBox cb = new CheckBox(symptoms.get(symptom));

      checkBoxesMap.put(symptom, cb);
      symptomContainer.getChildren().add(cb);
    }
  }

  private void toggleThresholdUI(boolean isVisible) {
    threshold.setVisible(isVisible);
    thresholdLabel.setVisible(isVisible);
    thresholdPercentage.setVisible(isVisible);
  }

  private boolean validateThreshold() {
    RadioButton selectedMethodRb = (RadioButton) method.getSelectedToggle();
    boolean isRuleBased = selectedMethodRb != null && selectedMethodRb.getText().equals("Rule-based");

    if (isRuleBased)
      return true;

    String input = threshold.getText();

    if (input.isEmpty()) {
      resultDisplay.setText("Threshold tidak boleh kosong.");
      return false;
    }

    try {
      double value = Double.parseDouble(input);
      if (value < 0 || value > 100) {
        resultDisplay.setText("Threshold harus antara 0 dan 100.");
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      resultDisplay.setText("Threshold harus berupa angka.");
      return false;
    }
  }
}