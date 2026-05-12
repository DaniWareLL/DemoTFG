package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.Song;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class PlaylistSongMenuController {

    private Playlist playlist;
    private Song currentSong;
    private Popup parentPopup;

    public void removePlaylistOnAction(ActionEvent actionEvent) {
        try {
            AppContext.getPlaylistService().deleteSongFromPlaylist(playlist, currentSong);

            Platform.runLater(() -> {
                AuxiliaryMethods.showPopup("Song removed from playlist: "+playlist.getName()+" correctly", (Stage) parentPopup.getOwnerWindow());
            });
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

    public void downloadOnAction(ActionEvent actionEvent) {

        AppContext.getExecutor().submit(() -> {
            try {
                AppContext.getDownloadService().downloadToMp3(currentSong.getOriginalUrl());

                Platform.runLater(() -> {
                    AuxiliaryMethods.showPopup("Song downloaded correctly", (Stage) parentPopup.getOwnerWindow());
                });

            } catch (AudioExtractorException e) {
                AuxiliaryMethods.showAlert(e);
            }
        });
        parentPopup.hide();
    }
}
