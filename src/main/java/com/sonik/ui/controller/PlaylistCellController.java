package com.sonik.ui.controller;

import com.sonik.domain.model.Playlist;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class PlaylistCellController {

    @FXML
    public Label nameLabel;
    @FXML
    public Label descriptionLabel;
    @FXML
    public Label countLabel;
    @FXML
    private ImageView thumbnailView;

    public void setPlaylist(Playlist playlist) {

        this.nameLabel.setText(playlist.getName());
        this.countLabel.setText(playlist.getSongs().size() + " songs");
    }
}
