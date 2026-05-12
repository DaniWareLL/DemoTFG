package com.sonik.ui.controller;

import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.Song;
import com.sonik.config.AppContext;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.List;

public class PlaylistSelectorController {

    @FXML
    public ListView<Playlist> playlistListView;
    @FXML
    private ObservableList<Playlist> playlistObservableList;
    @FXML
    public VBox playlistContainer;

    private Song selectedSong;

    private Popup parentPopup;

    public void setSong(Song newSong) {
        this.selectedSong = newSong;
    }

    public void setParentPopup(Popup popup) {
        this.parentPopup = popup;
    }

    public void initialize() {
        playlistListView.setCellFactory(list -> new PlaylistCell());
        playlistObservableList = FXCollections.observableArrayList();
        playlistListView.setItems(playlistObservableList);
        scanForPlaylists(playlistObservableList);

        playlistListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) addSongToPlaylist(newValue);
                });
    }

    private void addSongToPlaylist(Playlist playlist) {
        AppContext.getExecutor().submit(() -> {
            try {
                AppContext.getPlaylistService().addSongToPlaylist(playlist, selectedSong);
                Stage ownerStage = (Stage) parentPopup.getOwnerWindow();
                Platform.runLater(() -> {
                    AuxiliaryMethods.showPopup("Song added to playlist: "+playlist.getName()+" correctly", ownerStage);
                });

            } catch (IncorrectArgumentException | DuplicateIdException | DataAccessException |
                     ObjectNotFoundException e) {
                Platform.runLater(() -> {
                    Stage ownerStage = (Stage) parentPopup.getOwnerWindow();
                    AuxiliaryMethods.showPopup("Song can't be added to playlist: "+playlist.getName()+" because it already exists", ownerStage);
                    parentPopup.hide();
                });
            }
        });
        parentPopup.hide();
        playlistContainer.getScene().getWindow().hide();
    }

    protected static void scanForPlaylists(ObservableList<Playlist> playlistObservableList) {
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
