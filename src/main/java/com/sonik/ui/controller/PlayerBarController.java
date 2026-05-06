package com.sonik.ui.controller;

import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import com.sonik.domain.model.Song;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.javafx.Icon;

public class PlayerBarController {
    
    @FXML
    private Label songPlaying;

    @FXML
    private Label sourcePlaying;

    @FXML
    private Label qualityPlaying;

    @FXML
    private ImageView thumbnailPlaying;

    @FXML
    private FontIcon playIcon;

    @FXML
    private Button previousSongBtn;

    @FXML
    private Button playBtn;

    @FXML
    private Button nextSongBtn;

    @FXML
    private Slider progressBar;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label totalTimeLabel;

    @FXML
    private FontIcon muteIcon;

    private boolean isSeeking = false;

    public void initialize() {

        // Inicializar volumen desde VLCJ
        volumeSlider.setValue(AppContext.getAudioPlayer().getVolume());

        progressBar.setMouseTransparent(true);
        progressBar.setFocusTraversable(false);

        // Timer que actualiza cada 200ms
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(200), e -> updateProgress())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    public void playBtnMC(MouseEvent mouseEvent) {
        if(AppContext.getAudioPlayer().getCurrentUrl() == null) {
            return;
        }

        if (AppContext.getAudioPlayer().isPlaying()) {
            AppContext.getAudioPlayer().pause();
            playIcon.setIconLiteral("mdi2p-play-circle-outline");

        } else {
            AppContext.getAudioPlayer().resume();
            playIcon.setIconLiteral("mdi2p-pause-circle-outline");
        }
    }

    public void updateSongInfo(Song s) {
        if (s == null) return;

        progressBar.setMouseTransparent(false);
        progressBar.setFocusTraversable(true);

        songPlaying.setText(s.getTitle());
        sourcePlaying.setText(s.getSource());
        qualityPlaying.setText(UserSession.getPreferences().getStreamingQuality().toString());
        thumbnailPlaying.setImage(new Image(s.getThumbnailUrl(), true));
        playIcon.setIconLiteral("mdi2p-pause-circle-outline");

        int totalSec = s.getDurationSec();
        int min = totalSec / 60;
        int sec = totalSec % 60;
        totalTimeLabel.setText(String.format("%d:%02d", min, sec));


    }

    private void updateProgress() {
        if (!AppContext.getAudioPlayer().isPlaying() || isSeeking) return;

        int currentSec = AppContext.getAudioPlayer().getCurrentTimeSec();

        int min = currentSec / 60;
        int sec = currentSec % 60;

        Platform.runLater(() -> {
            currentTimeLabel.setText(String.format("%d:%02d", min, sec));
            progressBar.setValue(AppContext.getAudioPlayer().getPosition());
        });
    }


    public void seekStart(MouseEvent e) {
        // Pausar actualización automática mientras arrastra
        isSeeking = true;
    }

    public void seekEnd(MouseEvent e) {
        isSeeking = false;

        double newPos = progressBar.getValue(); // 0.0–1.0
        AppContext.getAudioPlayer().seekToPosition(newPos);
    }

    public void volumeChanged(MouseEvent mouseEvent) {
        int vol = (int) volumeSlider.getValue();
        AppContext.getAudioPlayer().setVolume(vol);
    }

    public void muteBtnMC(MouseEvent mouseEvent) {
        if(AppContext.getAudioPlayer().isMute()){
            AppContext.getAudioPlayer().setMute(false);
            muteIcon.setIconLiteral("mdi2v-volume-high");
        } else {
            AppContext.getAudioPlayer().setMute(true);
            muteIcon.setIconLiteral("mdi2v-volume-off");
        }
    }
}
