package com.sonik.ui.controller;

import com.sonik.domain.model.Playlist;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PlaylistCell extends ListCell<Playlist> {

    private final HBox root = new HBox();
    private final ImageView thumbnail = new ImageView();
    private final Label name = new Label();
    private final Label description = new Label();

    public PlaylistCell() {
        thumbnail.setFitWidth(32);
        thumbnail.setFitHeight(32);
        thumbnail.setPreserveRatio(true);
        thumbnail.setSmooth(true);

        root.setSpacing(12);
        root.setAlignment(Pos.CENTER_LEFT);
        root.getChildren().addAll(thumbnail, name);
    }

    @Override
    protected void updateItem(Playlist playlist, boolean empty) {
        super.updateItem(playlist, empty);

        if (empty || playlist == null) {
            setGraphic(null);
            return;
        }

        //thumbnail.setImage(playlist.get());
        name.setText(playlist.getName());
        description.setText(playlist.getDescription());

        setGraphic(root);
    }
}
