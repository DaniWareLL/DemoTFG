package com.sonik.ui.controller;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Song;
import com.sonik.domain.model.Playlist;
import com.sonik.config.AppContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.io.IOException;
import java.util.List;

public class SongOptionsMenuController {

    private Song song;          // canción asociada
    private Popup parentPopup;  // popup del menú principal

    public void setSong(Song song) {
        this.song = song;
    }

    public void setParentPopup(Popup popup) {
        this.parentPopup = popup;
    }

    @FXML
    private void anadirPlaylistBtnMC() {
        parentPopup.hide(); // cerrar menú principal

        showPlaylistSelector();
    }

    private void showPlaylistSelector() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playlist-selector-menu.fxml"));
            VBox selectorRoot = loader.load();

            PlaylistSelectorController controller = loader.getController();
            controller.setSong(song);

            Popup popup = new Popup();
            popup.getContent().add(selectorRoot);
            popup.setAutoHide(true);

            popup.show(selectorRoot.getScene().getWindow());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void descargarBtnMC() {
        try {
            AppContext.getExecutor().submit(() -> {
                AppContext.getDownloadService().downloadToMp3(song.getOriginalUrl());
            });

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Song downloaded correctly", ButtonType.OK);
                alert.showAndWait();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
