package com.sonik.ui.controller;

import com.sonik.domain.model.Song;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;

public class SongCell extends ListCell<Song> {

    private final HBox root = new HBox();
    private final Label id = new Label();
    private final Label title = new Label();
    private final Label time = new Label();
    private final Label source = new Label();
    private final Label quality = new Label();
    private final Label date = new Label();
    private final ImageView thumbnail = new ImageView();
    private final FontIcon options = new FontIcon("mdi2d-dots-vertical");

    public SongCell() {

        thumbnail.setFitWidth(32);
        thumbnail.setFitHeight(32);
        thumbnail.setPreserveRatio(true);
        thumbnail.setSmooth(true);

        root.setSpacing(20);
        root.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().addAll(id, title, time, source, quality, date, thumbnail, options);

        // Para que el título ocupe el espacio flexible
        HBox.setHgrow(title, Priority.ALWAYS);
    }

    @Override
    protected void updateItem(Song song, boolean empty) {
        super.updateItem(song, empty);

        if (empty || song == null) {
            setGraphic(null);
            return;
        }

        id.setText(String.valueOf(song.getId()));
        title.setText(song.getTitle());
        time.setText(String.valueOf(song.getDurationSec()));
        source.setText(song.getSources().toString());
        // quality.setText(song.getQuality());
        quality.setText("High");
        date.setText(String.valueOf(song.getAggregationDate()));
        thumbnail.setImage(new javafx.scene.image.Image(
                song.getThumbnailUrl(),
                32, 32,   // tamaño deseado
                true,     // preserveRatio
                true,     // smooth
                true      // backgroundLoading (NO bloquea la UI)
        ));

        setGraphic(root);
    }
}
