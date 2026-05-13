package com.sonik.ui.controller;


import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.model.Song;
import com.sonik.ui.navigation.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

import static com.sonik.ui.controller.AuxiliaryMethods.loadAndPlay;

/**
 * Handles the search views and mainly shows results
 */
public class SearchController {

    @FXML
    private ListView<Song> searchedSongsListView;

    public void initialize() {
        searchedSongsListView.setFixedCellSize(65);
        searchedSongsListView.setCellFactory(list -> new SongCell());

        searchedSongsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {

                int index = searchedSongsListView.getItems().indexOf(newVal);

                // Actualizo cola
                AppContext.getPlaybackQueueService().setQueue(
                        searchedSongsListView.getItems(),
                        index
                );
                // Hilo para extraer URL
                loadAndPlay(newVal);
            }
        });
    }

    public void setResults(List<Song> songs) {
        searchedSongsListView.getItems().setAll(songs);
        AppContext.getPlaybackQueueService().setQueue(songs, -1);
    }

}
