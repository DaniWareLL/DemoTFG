package com.sonik.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorWindowController {

    @FXML
    private Label errorMessage;
    @FXML
    private TextArea stackTraceTextArea;

    @FXML
    public void initialize() {

    }

    public void setErrorMessageAndStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        errorMessage.setText(throwable.getMessage());
        stackTraceTextArea.setText(sw.toString());
    }

    public void closeButtonActionPerformed(ActionEvent actionEvent) {
        ((Stage) stackTraceTextArea.getScene().getWindow()).close();
    }

}
