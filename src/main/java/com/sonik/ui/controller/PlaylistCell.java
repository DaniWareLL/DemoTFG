package com.sonik.ui.controller;

import com.sonik.domain.model.Playlist;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class PlaylistCell extends ListCell<Playlist> {

    private FXMLLoader loader;
    private PlaylistCellController controller;

    @Override
    protected void updateItem(Playlist playlist, boolean empty) {
        super.updateItem(playlist, empty);

        if (empty || playlist == null) {
            setGraphic(null);
            setText(null);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playlist-cell-view.fxml"));
            setGraphic(loader.load());

            PlaylistCellController controller = loader.getController();
            controller.setPlaylist(playlist);

        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e);
        }
    }
}
