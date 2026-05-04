package com.sonik.ui.controller;

import com.sonik.domain.model.Song;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import org.kordamp.ikonli.javafx.FontIcon;

public class SongCellController {

    @FXML private HBox root;
    @FXML private Label idLabel;
    @FXML private ImageView thumbnailView;
    @FXML private Label titleLabel;
    @FXML private Label timeLabel;
    @FXML private Label sourceLabel;
    @FXML private Label qualityLabel;
    @FXML private Label dateLabel;
    @FXML private FontIcon optionsIcon;

    private Song currentSong;

    public void initialize() {
        Rectangle clip = new Rectangle(40, 40);
        clip.setArcWidth(5);
        clip.setArcHeight(5);
        thumbnailView.setClip(clip);
    }

    public void setSong(Song song, int index) {
        this.currentSong = song;

        idLabel.setText(String.valueOf(index + 1));
        titleLabel.setText(song.getTitle());
        timeLabel.setText(formatDuration(song.getDurationSec()));
        qualityLabel.setText("High");
        sourceLabel.setText(song.getSource());

        dateLabel.setText(song.getAggregationDate() != null ?
                song.getAggregationDate().toString() : "");

        // Cargar thumbnail
        loadThumbnail(song.getThumbnailUrl());

        // Configurar evento de opciones
        optionsIcon.setOnMouseClicked(event -> showOptionsMenu());
    }

    private void loadThumbnail(String url) {
        if (url != null && !url.isEmpty()) {
            Image image = new Image(url, 40, 40, true, true, true);
            thumbnailView.setImage(image);
        }
    }

    private String formatDuration(int seconds) {
        if (seconds <= 0) return "0:00";
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", minutes, secs);
    }

    private void showOptionsMenu() {
        // Mostrar menú contextual
        System.out.println("Opciones para: " + currentSong.getTitle());
    }
}