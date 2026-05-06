package com.sonik.infrastructure.audio;

import com.sonik.config.AppConfig;
import com.sonik.domain.model.Song;
import com.sonik.service.audio.AudioPlayer;
import com.sun.jna.NativeLibrary;
import org.apache.commons.lang3.SystemUtils;
import uk.co.caprica.vlcj.binding.support.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;

import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Implementation of {@link AudioPlayer AudioPlayer} to play music through VLCj
 */
public class VlcjAudioPlayer implements AudioPlayer {

    private final MediaPlayerFactory factory;
    private final MediaPlayer player;
    private String currentUrl;
    private Song currentSong;

    private Runnable onPlaying;

    // Constructor
    public VlcjAudioPlayer() {

        factory = new MediaPlayerFactory();
        player = factory.mediaPlayers().newMediaPlayer();

        // Listener de eventos
        player.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                if (onPlaying != null) {
                    onPlaying.run();
                }
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                System.err.println("Error en la reproducción");
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                System.out.println("Stream finalizado");
            }
        });

    }


    @Override
    public void play(String streamUrl) {
        currentUrl = streamUrl;
        player.media().play(streamUrl);
    }

    @Override
    public void resume() {
        player.controls().play();
    }

    @Override
    public void pause() {
        player.controls().pause();
    }

    public void stop() {
        player.controls().stop();
    }

    @Override
    public void setOnPlaying(Runnable callback) {
        this.onPlaying = callback;
    }

    public void release() {
        player.release();
        factory.release();
    }

    @Override
    public boolean isPlaying() {
        return player.status().isPlaying();
    }

    @Override
    public void setCurrentSong(Song currentSong) {
        this.currentUrl = currentUrl;
    }

    @Override
    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    @Override
    public Song getCurrentSong() {
        return currentSong;
    }

    @Override
    public int getCurrentTimeSec() {
        return (int) (player.status().time() / 1000);
    }

    @Override
    public double getPosition() {
        return player.status().position(); // 0.0 – 1.0
    }

    @Override
    public void seekToPosition(double pos) {
        player.controls().setPosition((float) pos);
    }

    @Override
    public void setVolume(int volume) {
        player.audio().setVolume(volume);
    }

    @Override
    public int getVolume() {
        return player.audio().volume();
    }

    @Override
    public void setMute(boolean mute) {
        player.audio().setMute(mute);
    }

    @Override
    public boolean isMute() {
        return player.audio().isMute();
    }


}

/* CONTROLES BASICOS
 * player.controls().play();
 * player.controls().pause();
 * player.controls().stop();
 * player.controls().setPosition(float pos); // 0.0 a 1.0
 * player.controls().setTime(long ms);
 * player.controls().skipTime(long ms);*/

/* CONTROLES DE AUDIO
 * player.audio().setVolume(int volume); // 0–100
 * player.audio().getVolume();
 * player.audio().mute();
 * player.audio().unmute();
 * player.audio().isMuted();
 * */

/* ESTADO DEL REPRODUCTOR
 * player.status().isPlaying();
 * player.status().isPaused();
 * player.status().isSeekable();
 * player.status().length();   // duración en ms
 * player.status().time();     // tiempo actual en ms
 * player.status().position(); // 0.0 a 1.0*/