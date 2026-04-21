package com.sonik.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;

public class SettingsController {

    // Elementos del FXML
    @FXML
    private ChoiceBox<String> quealityCB;  // Nota: tiene typo "queality" en lugar de "quality"
    @FXML
    private ChoiceBox<String> sourceCB;
    @FXML
    private ChoiceBox<String> themeCB;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Button backHomeButton;
}
