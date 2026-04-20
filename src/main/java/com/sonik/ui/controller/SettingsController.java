package com.sonik.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class SettingsController {
    // En tu MainController.java
    @FXML
    private BorderPane mainContainer;  // El contenedor principal

    @FXML
    private void openSettingsPanel() {
        // NO creas nueva ventana, SOLO cambias el contenido
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/settings.fxml"));
            Node settingsPanel = loader.load();  // ← Es solo un nodo, no una ventana

            // Reemplazas el contenido CENTRAL de tu ventana principal
            mainContainer.setCenter(settingsPanel);  // ← REEMPLAZA, no crea nueva

            // La ventana principal SIGUE SIENDO LA MISMA
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// Resultado: El usuario ve UNA SOLA ventana, solo cambió el contenido
}
