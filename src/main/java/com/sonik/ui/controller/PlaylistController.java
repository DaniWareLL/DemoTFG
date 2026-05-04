package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.Song;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class PlaylistController {

    @FXML
    private ListView<Song> songListView;

    private Playlist playlist;

    public void initialize() {
        songListView.setCellFactory(list -> new SongCell());

    }

    public void searchBarOnKP(KeyEvent keyEvent) {

    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        List<Song> songs = AppContext.getPlaylistService().getSongs(playlist);
        songListView.getItems().setAll(songs);
    }

}
