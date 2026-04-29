package com.sonik.ui.controller;

import com.sonik.domain.model.Song;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;

public class PlaylistController {

    @FXML
    private ListView<Song> songListView;

    public void initialize() {
        songListView.setCellFactory(list -> new SongCell());
    }

    public void searchBarOnKP(KeyEvent keyEvent) {

    }

}
