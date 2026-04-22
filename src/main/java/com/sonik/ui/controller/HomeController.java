package com.sonik.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class HomeController {

    @FXML
    private BorderPane mainContainer;

    // --- Top bar / búsqueda ---
    @FXML
    private TextField SearchBar;

    @FXML
    private Button ButtonOptions;

    // --- Menú lateral ---
    @FXML
    private Button ButtonHome;

    @FXML
    private Button ButtonExplore;

    @FXML
    private Button ButtonPlaylist;

    @FXML
    private Button ButtonSongs;

    // --- Reproductor inferior ---
    @FXML
    private Button ButtonPreviousSong;

    @FXML
    private Button ButtonPlay;

    @FXML
    private Button ButtonNextSong;

    @FXML
    private ProgressBar ProgressBar;

    @FXML
    private Slider VolumeSlider;

    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label totalTimeLabel;

    public void settingButtonMC(MouseEvent mouseEvent) {
        try {
            // Cargar ajustes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/settings-view.fxml"));
            Node settingsPanel = loader.load();

            // Configurar controller
            SettingsController controller = loader.getController();

            // SOLO cambiar el centro - izquierda y abajo se quedan igual
            mainContainer.setCenter(settingsPanel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
