package com.diseaseexpertsystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  private Map<String, CheckBox> checkBoxMap = new HashMap<>();

  /* lifecycle handler */

  @FXML
  private void initialize() {
    renderCheckBoxes();
  }

  private void renderCheckBoxes() {
    List<String> semuaGejala = List.of(
        "Diare",
        "Nyeri Otot",
        "Lelah",
        "Sakit Tenggorokan",
        "Hilang Indra Penciuman");

    for (String gejala : semuaGejala) {
      CheckBox cb = new CheckBox(gejala);

      checkBoxMap.put(gejala, cb);
      symptomContainer.getChildren().add(cb);
    }
  }

  /* interaction handler */

  @FXML
  void diagnoseDisease(ActionEvent event) {
    StringBuilder sb = new StringBuilder();
    sb.append("Gejala yang dipilih:\n");

    for (Map.Entry<String, CheckBox> entry : checkBoxMap.entrySet()) {
      if (entry.getValue().isSelected()) {
        sb.append("- ").append(entry.getKey()).append("\n");
      }
    }

    resultDisplay.setText(sb.toString());
  }

  @FXML
  void resetSymptom(ActionEvent event) {
    for (CheckBox cb : checkBoxMap.values()) {
      cb.setSelected(false);
    }

    resultDisplay.clear();
  }
}
