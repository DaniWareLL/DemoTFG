package com.sonik.ui.controller;

import com.sonik.domain.model.Song;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class SearchController_new {
    @FXML
    private VBox resultsContainer;

    public void loadResults(List<Song> songs) throws IOException {
        resultsContainer.getChildren().clear();

        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/song-cell-view.fxml"));
            Node cell = loader.load();

            SongCellController controller = loader.getController();
            controller.setSong(song, i); // ← AQUÍ LE PASAS EL ÍNDICE

            resultsContainer.getChildren().add(cell);
        }
    }

}
