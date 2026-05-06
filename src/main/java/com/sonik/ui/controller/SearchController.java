package com.sonik.ui.controller;


import com.sonik.config.AppContext;
import com.sonik.domain.model.Song;
import com.sonik.ui.navigation.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

/**
 * Handles the search views and mainly shows results
 */
public class SearchController {

    @FXML
    private ListView<Song> searchedSongsList;

    public void initialize() {
        searchedSongsList.setFixedCellSize(65);
        searchedSongsList.setCellFactory(list -> new SongCell());

        searchedSongsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Hilo para extraer URL
                AppContext.getExecutor().submit(() -> {
                    try {
                        String url = AppContext.getPlayerService().getStreamUrl(newVal.getOriginalUrl());
                        if (!url.isEmpty()) {
                            // Hilo para reproducir
                            AppContext.getExecutor().submit(() -> {
                                AppContext.getAudioPlayer().setCurrentSong(newVal);
                                System.out.println(newVal);
                                System.out.println(url);
                                AppContext.getAudioPlayer().play(url);
                                PlayerBarController playerBar = ViewManager.getPlayerBarController();

                                Platform.runLater(() -> {
                                    playerBar.updateSongInfo(newVal);
                                });
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });


    }

    public void setResults(List<Song> songs) {
        searchedSongsList.getItems().setAll(songs);
    }

}
