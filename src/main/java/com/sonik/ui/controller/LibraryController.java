package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.model.Song;
import com.sonik.ui.navigation.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;

import java.util.List;


public class LibraryController {

    @FXML
    private ListView<Song> favouriteSongsListView;

    public void initialize() {
        favouriteSongsListView.setFixedCellSize(65);
        favouriteSongsListView.setCellFactory(list -> new SongCell());

        AppContext.getExecutor().submit(() -> {
            List<Song> favouriteSongs = AppContext.getLibraryService().getFavouriteSongs();
            Platform.runLater(() -> {
                setResults(favouriteSongs);
            });
        });

        favouriteSongsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Hilo para extraer URL
                AppContext.getExecutor().submit(() -> {
                    try {
                        System.out.println(newVal);
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
                    } catch (AudioExtractorException e) {
                        AuxiliaryMethods.showAlert(e.getMessage());
                    }
                });
            }
        });
    }
    public void setResults(List<Song> songs) {
        favouriteSongsListView.getItems().setAll(songs);
    }


    public void searchBarOnKP(KeyEvent keyEvent) {
    }
}
