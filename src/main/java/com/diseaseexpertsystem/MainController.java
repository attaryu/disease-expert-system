package com.diseaseexpertsystem;

import java.util.HashMap;
import java.util.Map;

import com.diseaseexpertsystem.engine.InferenceEngine;
import com.diseaseexpertsystem.knowledge.DiseaseKnowledgeBaseAbstract;
import com.diseaseexpertsystem.knowledge.knowledges.GastroenteritisKnowledgeBase;
import com.diseaseexpertsystem.knowledge.knowledges.InfectionKnowledgeBase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
  private VBox symptomContainer;

  @FXML
  private TextArea resultDisplay;

  @FXML
  private TextField threshold;

  /* custom variable */

  private DiseaseKnowledgeBaseAbstract infectionKnowledgeBase = new InfectionKnowledgeBase();
  private DiseaseKnowledgeBaseAbstract gastroenteritisKnowledgeBase = new GastroenteritisKnowledgeBase();
  private InferenceEngine engine = new InferenceEngine(infectionKnowledgeBase);
  private Map<String, CheckBox> checkBoxesMap = new HashMap<>();

  /* lifecycle handler */

  @FXML
  private void initialize() {
    renderCheckBoxes();
    listenDiseaseToogleGroup();
    threshold.setText("0");
  }

  private void listenDiseaseToogleGroup() {
    diseaseGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
      if (newToggle == null) {
        symptomContainer.getChildren().clear();
        checkBoxesMap.clear();

        return;
      }

      String selectedDisease = ((RadioButton) newToggle).getText();

      if (selectedDisease.equals("Infeksi/Non-infeksi")) {
        engine.setKnowledgeBase(infectionKnowledgeBase);
      } else if (selectedDisease.equals("Gastrousus")) {
        engine.setKnowledgeBase(gastroenteritisKnowledgeBase);
      }

      symptomContainer.getChildren().clear();
      checkBoxesMap.clear();

      renderCheckBoxes();
    });
  }

  private void renderCheckBoxes() {
    Map<String, String> symptoms = engine.getKnowledgeBase().getSymptoms();

    for (String symptom : symptoms.keySet()) {
      CheckBox cb = new CheckBox(symptoms.get(symptom));

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
