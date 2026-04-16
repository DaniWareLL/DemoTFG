package com.sonik.infrastructure.audio;

import com.sonik.service.audio.AudioPlayer;
import com.sun.jna.NativeLibrary;
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

    public static Scanner sc = new Scanner(System.in);

    private static final String VLC_PATH;

    static {
        if (RuntimeUtil.isWindows()) {
            VLC_PATH = Paths.get("bin", "libvlc-win").toAbsolutePath().toString();
        } else {
            VLC_PATH = Paths.get("bin", "libvlc-linux").toAbsolutePath().toString();
        }

        NativeLibrary.addSearchPath("libvlc", VLC_PATH);
        System.setProperty("jna.library.path", VLC_PATH);
    }


    private final MediaPlayerFactory factory;
    private final MediaPlayer player;

    // Constructor
    public VlcjAudioPlayer() {

        String pluginPath = Paths.get(VLC_PATH, "plugins").toString();

        factory = new MediaPlayerFactory("--plugin-path=" + pluginPath);
        player = factory.mediaPlayers().newMediaPlayer();

        // Listener de eventos (hay que mirarlo)
        player.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                System.out.println("Playing...");
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

    /**
     * Reproduce la URL usando VLCJ
     */
    public void playStream(String streamUrl) {
        player.media().play(streamUrl);
        System.out.println("[Enter] to finish...");
        sc.nextLine();
    }

    public void stop() {
        player.controls().stop();
    }

    public void release() {
        player.release();
        factory.release();
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
}
