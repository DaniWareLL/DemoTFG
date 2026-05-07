package com.sonik.ui.navigation;


import com.sonik.ui.controller.PlayerBarController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
    public static void switchScene(ViewType viewType) throws IOException {
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
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
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
    }
        // Cargar vista dentro del centro del BorderPane
    public static void loadIntoCenter(ViewType viewType) throws IOException {
        FXMLLoader loader = new FXMLLoader(viewType.getUrl());
        Node view = loader.load();
        mainContainer.setCenter(view);
    }

    // Cargar vista dentro del centro del BorderPane
    public static void loadIntoLeft(ViewType viewType) throws IOException {
        FXMLLoader loader = new FXMLLoader(viewType.getUrl());
        Node view = loader.load();
        mainContainer.setLeft(view);
    }

    // Cargar vista dentro del centro del BorderPane
    public static void loadIntoBottom(ViewType viewType) throws IOException {
        FXMLLoader loader = new FXMLLoader(viewType.getUrl());
        Node view = loader.load();

        // Añade esto para guardar el controller cuando se carga el PlayerBar
        if (viewType == ViewType.PLAYER_BOTTOMBAR) {
            playerBarController = loader.getController();
        }
        mainContainer.setBottom(view);
    }

    public static <T> T loadIntoCenterWithController(ViewType viewType) throws IOException {
        FXMLLoader loader = new FXMLLoader(viewType.getUrl());
        Node view = loader.load();
        mainContainer.setCenter(view);
        return loader.getController();
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
