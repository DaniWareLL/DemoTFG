package com.sonik.ui.controller;

import com.sonik.domain.model.Song;
import com.sonik.ui.navigation.ViewType;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class SongCellPlaylist extends ListCell<Song> {

    private FXMLLoader loader;
    private SongCellPlaylistController controller;

    @Override
    protected void updateItem(Song song, boolean empty) {
        super.updateItem(song, empty);

        if (empty || song == null) {
            setGraphic(null);
            return;
        }

        if (loader == null) {
            try {
                loader = new FXMLLoader(ViewType.SONG_CELL_PLAYLIST.getUrl());
                setGraphic(loader.load());
                controller = loader.getController();
            } catch (IOException e){
                AuxiliaryMethods.showAlert(e);
                return;
            }
        }

        controller.setSongAndPlaylist(song, getIndex(), PlaylistController.getPlaylist());
    }

}
