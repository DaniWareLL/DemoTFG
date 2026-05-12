package com.sonik.ui.controller;

import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.model.Song;
import com.sonik.config.AppContext;
import com.sonik.ui.navigation.ViewType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

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
    private void addToPlaylistBtnMC() {
        parentPopup.hide(); // cerrar menú principal

        showPlaylistSelector();
    }

    private void showPlaylistSelector() {
        try {
            FXMLLoader loader = new FXMLLoader(ViewType.SONG_OPTIONS.getUrl());
            VBox selectorRoot = loader.load();

            PlaylistSelectorController controller = loader.getController();
            controller.setSong(song);
            controller.setParentPopup(parentPopup);

            Popup popup = new Popup();
            popup.getContent().add(selectorRoot);
            popup.setAutoHide(true);

            popup.show(parentPopup.getOwnerWindow());

        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e);
        }
    }

    @FXML
    private void downloadBtnMC() {

        AppContext.getExecutor().submit(() -> {
            try {
                AppContext.getDownloadService().downloadToMp3(song.getOriginalUrl());

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
