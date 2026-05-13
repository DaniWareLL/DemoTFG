package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.Song;
import com.sonik.ui.navigation.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class PlaylistController {

    @FXML
    public Label playlistName;
    @FXML
    public Label description;
    @FXML
    private ListView<Song> songsListView;

    private static Playlist playlist;

    @FXML
    public TextField searchBar;

    private static PlaylistController instance;

    public static PlaylistController getInstance() {
        return instance;
    }

    public void initialize() {
        instance = this;
        songsListView.setFixedCellSize(65);
        songsListView.setCellFactory(list -> new SongCellPlaylist());

        AppContext.getExecutor().submit(() -> {
            List<Song> songs = AppContext.getPlaylistService().getSongs(playlist);
            Platform.runLater(() -> {
                songsListView.getItems().setAll(songs);
            });
        });

        songsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {

                int index = songsListView.getItems().indexOf(newVal);

                // Actualizo cola
                AppContext.getPlaybackQueueService().setQueue(
                        songsListView.getItems(),
                        index
                );

                // Hilo para extraer URL
                AppContext.getExecutor().submit(() -> {
                    try {
                        System.out.println(newVal);
                        String url = AppContext.getPlayerService().getStreamUrl(newVal.getOriginalUrl());
                        if (!url.isEmpty()) {
                            // Hilo para reproducir
                            AppContext.getExecutor().submit(() -> {
                                AppContext.getAudioPlayer().setCurrentSong(newVal);
                                System.out.println(newVal);
                                System.out.println(url);
                                AppContext.getAudioPlayer().play(url);
                                PlayerBarController playerBar = ViewManager.getPlayerBarController();

                                Platform.runLater(() -> {
                                    playerBar.updateSongInfo(newVal);
                                });
                            });

                        }
                    } catch (AudioExtractorException e) {
                        AuxiliaryMethods.showAlert(e);
                    }
                });
            }
        });

    }

    public void loadPlaylist(Playlist newPlaylist) {
        playlistName.setText(newPlaylist.getName());
        description.setText(newPlaylist.getDescription());
        playlist = newPlaylist;
        refreshSongs();
    }

    public void refreshSongs() {
        AppContext.getExecutor().submit(() -> {
            List<Song> songs = AppContext.getPlaylistService().getSongs(playlist);
            Platform.runLater(() -> {
                songListView.getItems().clear();
                songListView.setCellFactory(list -> new SongCellPlaylist());
                songListView.getItems().setAll(songs);
            });
        });
    }

    public static Playlist getPlaylist() {
        return playlist;
    }

    public void searchBarOnKP(KeyEvent keyEvent) {
    }
}
