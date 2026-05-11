package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PlaylistSidebarController {

    @FXML
    private ListView<Playlist> playlistListView;

    private ObservableList<Playlist> playlistObservableList;

    public void initialize() {
        playlistListView.setCellFactory(list -> new PlaylistCell());
        playlistObservableList = FXCollections.observableArrayList();
        playlistListView.setItems(playlistObservableList);
        scanForPlaylists();
    }

    public void createPlaylistOnMC(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/create-playlist-dialog.fxml"));
        DialogPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e);
        }
        pane.setMinWidth(450);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(pane);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.getDialogPane().setStyle("-fx-background-color: transparent;");
        dialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);
        dialog.setTitle("Crear playlist");
        dialog.showAndWait();
        playlistListView.setCellFactory(list -> new PlaylistCell());
        playlistObservableList = FXCollections.observableArrayList();
        playlistListView.setItems(playlistObservableList);
        scanForPlaylists();

    }

    public void scanForPlaylists() {
        PlaylistSelectorController.scanForPlaylists(playlistObservableList);
    }

    public void deletePlaylistOnMC(MouseEvent mouseEvent) {

        Playlist toDelete = playlistListView.getSelectionModel().getSelectedItem();
        if (toDelete != null) {

            Platform.runLater(() -> {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initStyle(StageStyle.TRANSPARENT);
                alert.getDialogPane().getScene().setFill(Color.TRANSPARENT);
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this playlist?\nThis action CANNOT be undone.");
                alert.setGraphic(null);

                ButtonType deleteBtn = new ButtonType("Delete");
                ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(deleteBtn, cancelBtn);

                DialogPane pane = alert.getDialogPane();
                pane.getStylesheets().add(
                        new File("./src/main/resources/css/alert-dialog.css").toURI().toString());

                Button delete = (Button) pane.lookupButton(deleteBtn);
                delete.setStyle("-fx-background-color: #c62828; -fx-text-fill: white; -fx-background-radius: 8;");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == deleteBtn) {
                    try {
                        AppContext.getPlaylistService().deletePlaylist(toDelete);
                    } catch (ObjectNotFoundException | DataAccessException e) {
                        AuxiliaryMethods.showAlert(e);
                    }
                }

                playlistListView.setCellFactory(list -> new PlaylistCell());
                playlistObservableList = FXCollections.observableArrayList();
                playlistListView.setItems(playlistObservableList);
                scanForPlaylists();
            });

        }
    }
}
