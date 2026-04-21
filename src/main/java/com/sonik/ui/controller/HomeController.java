package com.sonik.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HomeController {

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
    private Label currentTimeLabel;   // si quieres, puedes enlazar al primer "0:00"

    @FXML
    private Label totalTimeLabel;     // y al segundo "0:00"
}
