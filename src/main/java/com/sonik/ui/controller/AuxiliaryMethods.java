package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.model.Song;
import com.sonik.ui.navigation.ViewManager;
import com.sonik.ui.navigation.ViewType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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

    protected static void showPopup(String message, Stage ownerStage) {
        Platform.runLater(() -> {
            // Crear un Stage personalizado
            Stage popupStage = new Stage();
            popupStage.initStyle(StageStyle.TRANSPARENT); // Sin bordes ni decoración
            popupStage.initModality(Modality.NONE); // No bloqueante

            // Contenido del popup
            VBox content = new VBox();
            content.setAlignment(Pos.CENTER);
            content.setPadding(new Insets(15, 30, 15, 30));

            Label label = new Label(message);
            label.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-font-family: 'Manrope Medium'");

            content.getChildren().add(label);
            content.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.85); " +
                            "-fx-background-radius: 10; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2);"
            );

            Scene scene = new Scene(content);
            scene.setFill(Color.TRANSPARENT);
            popupStage.setScene(scene);

            // Posicionar en el centro de la ventana actual
            popupStage.setX(ownerStage.getX() + ownerStage.getWidth()/2 - 100);
            popupStage.setY(ownerStage.getY() + ownerStage.getHeight()/2 - 30);

            // Efecto de fade in
            popupStage.setOpacity(0);
            popupStage.show();

            Timeline fadeIn = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(popupStage.opacityProperty(), 0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(popupStage.opacityProperty(), 1))
            );
            fadeIn.play();

            // Desaparecer automáticamente después de 3 segundos con fade out
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(3), e -> {
                        // Fade out
                        Timeline fadeOut = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(popupStage.opacityProperty(), 1)),
                                new KeyFrame(Duration.millis(300), new KeyValue(popupStage.opacityProperty(), 0))
                        );
                        fadeOut.setOnFinished(ev -> popupStage.close());
                        fadeOut.play();
                    })
            );
            timeline.play();
        });
    }

    protected static void loadAndPlay(Song song) {
        AppContext.getExecutor().submit(() -> {
            try {
                String url = AppContext.getPlayerService().getStreamUrl(song.getOriginalUrl());
                if (!url.isEmpty()) {
                    // Hilo para reproducir
                    AppContext.getExecutor().submit(() -> {
                        AppContext.getAudioPlayer().setCurrentSong(song);
                        AppContext.getAudioPlayer().play(url);
                        PlayerBarController playerBar = ViewManager.getPlayerBarController();

                        Platform.runLater(() -> {
                            playerBar.updateSongInfo(song);
                        });
                    });

                }
            } catch (AudioExtractorException e) {
                AuxiliaryMethods.showAlert(e);
            }
        });
    }


}
