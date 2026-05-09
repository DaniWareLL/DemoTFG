package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

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
    @FXML private FontIcon likeIcon;

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
        qualityLabel.setText(UserSession.getPreferences().getStreamingQuality().toString());
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

    public void optionsBtnMC(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/song-options-menu.fxml"));
            VBox menuRoot = loader.load();

            SongOptionsMenuController controller = loader.getController();
            controller.setSong(currentSong);

            Popup popup = new Popup();
            popup.getContent().add(menuRoot);
            popup.setAutoHide(true);

            controller.setParentPopup(popup);

            double x = optionsIcon.localToScreen(optionsIcon.getBoundsInLocal()).getMinX();
            double y = optionsIcon.localToScreen(optionsIcon.getBoundsInLocal()).getMaxY();

            popup.show(optionsIcon.getScene().getWindow(), x, y);

        } catch (IOException e) {
            AuxiliaryMethods.showAlert(e.getMessage());
        }
    }

    public void likeBtnMC(MouseEvent mouseEvent) {
    }

    public void likeBtnOnAction(ActionEvent actionEvent) {
        try {
            if(AppContext.getJpaUserLibraryRepository().exists(UserSession.getUser().getId(), currentSong.getId())){
                AppContext.getLibraryService().removeFavouriteSong(currentSong);
                likeIcon.setIconLiteral("mdi2h-heart-outline");
            } else {
                try {
                    AppContext.getJpaSongRepository().findById(currentSong.getId());
                }catch (ObjectNotFoundException e) {
                    AppContext.getJpaSongRepository().save(currentSong);
                }
                AppContext.getLibraryService().addFavouriteSong(currentSong);
                likeIcon.setIconLiteral("mdi2h-heart");
            }
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (IncorrectArgumentException e) {
            e.printStackTrace();
        } catch (DuplicateIdException e) {
            e.printStackTrace();
        }
    }
}