package com.sonik.ui.controller;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.Song;
import com.sonik.config.AppContext;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class PlaylistSelectorController {

    @FXML
    private VBox playlistContainer;

    private Song song;

    public void setSong(Song song) throws ObjectNotFoundException, DataAccessException {
        this.song = song;
        loadPlaylists();
    }

    private void loadPlaylists() throws ObjectNotFoundException, DataAccessException {
        try {
            List<Playlist> playlists = AppContext.getJpaPlaylistRepositor().getAllPlaylist();

            for (Playlist p : playlists) {

                Label item = new Label(p.getName());

                item.setStyle("-fx-text-fill: white; -fx-font-size: 14; -fx-padding: 5 10;");

                item.setOnMouseClicked(e -> {
                    try {
                        AppContext.getPlaylistService().addSongToPlaylist(p, song, 0);
                    } catch (IncorrectArgumentException ex) {
                        ex.printStackTrace();
                    } catch (DuplicateIdException ex) {
                        System.out.println("Cancion Duplicada error");
                    }
                });

                playlistContainer.getChildren().add(item);
            }
        } catch (ObjectNotFoundException e){
            System.out.println("Popup no hay playlist creadas, cree antes una");
        }
    }
}
