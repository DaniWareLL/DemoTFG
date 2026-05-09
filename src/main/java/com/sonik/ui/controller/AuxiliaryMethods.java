package com.sonik.ui.controller;

import com.sonik.ui.navigation.ViewType;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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
        textField.setStyle(
                "-fx-background-radius: 20px; " +
                        "-fx-background-color: transparent; " +
                        "-fx-border-color: " + color + "; " +
                        "-fx-border-radius: 20px; " +
                        "-fx-text-fill: white;");
    }

    /**
     * Displays an error window containing the exception message and its stack trace
     * @param exception The exception that was thrown
     */
    protected static void showAlert(Throwable exception) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewType.ERROR_WINDOW.getUrl());
            VBox root = loader.load();

            // Get the controller and set the error details
            ErrorWindowController controller = loader.getController();
            controller.setErrorMessageAndStackTrace(exception);

            // Create and show the stage
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
