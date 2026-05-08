package com.sonik.ui.controller;

import com.sonik.config.UserSession;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlaylistSelectorController {

    @FXML
    private VBox playlistContainer;

    private Song selectedSong;

    public void setSong(Song newSong) {
        this.selectedSong = newSong;
    }

    private void addSongToPlaylist(Playlist playlist) {
        try {
            AppContext.getPlaylistService().addSongToPlaylist(playlist, selectedSong);
        } catch (IncorrectArgumentException | DuplicateIdException | DataAccessException e) {
            AuxiliaryMethods.showAlert(e);
        }
        playlistContainer.getScene().getWindow().hide();
    }


    static void scanForPlaylists(ObservableList<Playlist> playlistObservableList) {
        List<Playlist> playlists = List.of();
        try {
            playlists = AppContext.getPlaylistService().findAllPlaylistsForUser(UserSession.getUser().getUserName());
        } catch (DataAccessException | ObjectNotFoundException | IncorrectArgumentException e) {
            AuxiliaryMethods.showAlert(e);
        }
        playlistObservableList.clear();
        playlistObservableList.addAll(playlists);
    }
}
