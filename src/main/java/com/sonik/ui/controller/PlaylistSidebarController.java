package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.model.Playlist;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class PlaylistSidebarController {
    @FXML
    private ListView<Playlist> playlistListView;

    private Dialog<?> dialog;

    public void initialize() {
        playlistListView.setCellFactory(list -> new PlaylistCell());
    }

    public void createPlaylistOnMC(MouseEvent mouseEvent) throws IncorrectArgumentException, DuplicateIdException, DataAccessException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/create-playlist-dialog.fxml"));
        DialogPane pane = loader.load();

        CreatePlaylistDialogController controller = loader.getController();
        controller.setDialog(dialog);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(pane);
        dialog.setTitle("Crear playlist");


        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Playlist playlist = controller.buildPlaylist();
            AppContext.getPlaylistService().createPlaylist(playlist);
        }

    }

    public void deletePlaylistOnMC(MouseEvent mouseEvent) {

    }
}
