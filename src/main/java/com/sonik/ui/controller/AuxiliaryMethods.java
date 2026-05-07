package com.sonik.ui.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Optional;

public class AuxiliaryMethods {

    /**
     * Changes the visibility, styling and text (if desired) of an {@link javafx.scene.control.Label error label}
     * @param state Whether to enable or disable the label ({@code true} makes the label visible and {@code false} makes it invisible)
     * @param label The label to be changed
     * @param textField The {@link javafx.scene.control.TextField} associated to the label
     * @param message The text to be shown in the label (if it is empty, the message shown will be the one initially present in the label)
     */
    protected static void setErrorLabelState(boolean state, Label label, TextField textField, Optional<String> message) {

        String color = state ? "red" : "white";
        label.setVisible(state);
        message.ifPresent(value -> label.setText(value));
        label.setManaged(state);
        textField.setStyle(
                "-fx-background-radius: 20px; " +
                        "-fx-background-color: transparent; " +
                        "-fx-border-color: " + color + "; " +
                        "-fx-border-radius: 20px; " +
                        "-fx-text-fill: white;");
    }

    /**
     * Displays an {@link javafx.scene.control.Alert} onscreen containing the desired message
     * @param message The message to be displayed
     */
    protected static void showAlert(String message) {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    message, ButtonType.OK);
            alert.showAndWait();});
    }

}
