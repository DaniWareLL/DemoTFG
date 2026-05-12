package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.Song;
import javafx.event.ActionEvent;
import javafx.stage.Popup;

public class RemoveSongMenuController {

    private Playlist playlist;
    private Song currentSong;
    private Popup parentPopup;

    public void removePlaylistOnAction(ActionEvent actionEvent) {
        try {
            AppContext.getPlaylistService().deleteSongFromPlaylist(playlist, currentSong);
        } catch (ObjectNotFoundException | DataAccessException e) {
            AuxiliaryMethods.showAlert(e);
        }
        parentPopup.hide();
    }

    public void setSong(Song song) {
        this.currentSong = song;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void setParentPopup(Popup parentPopup) {
        this.parentPopup = parentPopup;
    }
}
