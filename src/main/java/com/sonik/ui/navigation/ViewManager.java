package com.sonik.ui.navigation;


import com.sonik.ui.controller.PlayerBarController;
import com.sonik.ui.controller.SignInController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Manages views and changes between them
 */
public class ViewManager {

    private static Stage primaryStage;
    private static BorderPane mainContainer;
    private static PlayerBarController playerBarController;

    // Inicializar desde Main o HomeController
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void setMainContainer(BorderPane container) {
        mainContainer = container;
    }

    // Cambiar escena completa
    public static void switchScene(ViewType viewType) {
        try {
            FXMLLoader loader = new FXMLLoader(viewType.getUrl());
            Scene scene = new Scene(loader.load());

            // Obtener controller clase Object genérico
            Object controller = loader.getController();

            // Crear nuevo Stage
            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setScene(scene);

            // Si el controller tiene setStage(Stage), llamarlo
            try {
                controller.getClass()
                        .getMethod("setStage", Stage.class)
                        .invoke(controller, newStage);
            } catch (NoSuchMethodException ignored) {
                // El controller no necesita stage
            }

            // Mostrar nuevo Stage
            newStage.show();

            // Cerrar el anterior
            if (primaryStage != null) {
                primaryStage.close();
            }

            // Actualizar referencia
            primaryStage = newStage;

        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK);
                alert.showAndWait();
            });
            e.printStackTrace();
        }
    }

    // Cargar vista dentro del centro del BorderPane
    public static void loadIntoCenter(ViewType viewType) {
        try {
            FXMLLoader loader = new FXMLLoader(viewType.getUrl());
            Node view = loader.load();
            mainContainer.setCenter(view);
        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK);
                alert.showAndWait();
            });
            e.printStackTrace();
        }
    }

    // Cargar vista dentro del centro del BorderPane
    public static void loadIntoLeft(ViewType viewType) {
        try {
            FXMLLoader loader = new FXMLLoader(viewType.getUrl());
            Node view = loader.load();
            mainContainer.setLeft(view);
        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK);
                alert.showAndWait();
            });
            e.printStackTrace();
        }
    }

    // Cargar vista dentro del centro del BorderPane
    public static void loadIntoBottom(ViewType viewType) {
        try {
            FXMLLoader loader = new FXMLLoader(viewType.getUrl());
            Node view = loader.load();

            // Añade esto para guardar el controller cuando se carga el PlayerBar
            if (viewType == ViewType.PLAYER_BOTTOMBAR) {
                playerBarController = loader.getController();
            }

            mainContainer.setBottom(view);
        } catch (Exception e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK);
                alert.showAndWait();
            });
            e.printStackTrace();
        }
    }

    public static <T> T loadIntoCenterWithController(ViewType viewType) {
        try {
            FXMLLoader loader = new FXMLLoader(viewType.getUrl());
            Node view = loader.load();
            mainContainer.setCenter(view);
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public class NavigationFlags {
        public static boolean showAccountCreated = false;
    }

    public static PlayerBarController getPlayerBarController() {
        return playerBarController;
    }
}
