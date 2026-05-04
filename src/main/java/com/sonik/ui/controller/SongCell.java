package com.sonik.ui.controller;

import com.sonik.domain.model.Song;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class SongCell extends ListCell<Song> {

    private FXMLLoader loader;
    private SongCellController controller;

    @Override
    protected void updateItem(Song song, boolean empty) {
        super.updateItem(song, empty);

        if (empty || song == null) {
            setGraphic(null);
            return;
        }

        if (loader == null) {
            try {
                loader = new FXMLLoader(getClass().getResource("/views/song-cell-view.fxml"));
                setGraphic(loader.load());
                controller = loader.getController();
            } catch (Exception e) {
                e.printStackTrace();
                setText("Error loading cell");
                return;
            }
        }

        controller.setSong(song, getIndex());
    }
}