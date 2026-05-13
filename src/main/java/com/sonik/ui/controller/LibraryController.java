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

import static com.sonik.ui.controller.AuxiliaryMethods.loadAndPlay;


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

                int index = favouriteSongsListView.getItems().indexOf(newVal);

                // Actualizo cola
                AppContext.getPlaybackQueueService().setQueue(
                        favouriteSongsListView.getItems(),
                        index
                );

                // Hilo para extraer URL
                loadAndPlay(newVal);
            }
        });
    }
    public void setResults(List<Song> songs) {
        favouriteSongsListView.getItems().setAll(songs);
        AppContext.getPlaybackQueueService().setQueue(songs, -1);
    }


    public void searchBarOnKP(KeyEvent keyEvent) {
    }
}
