package com.sonik.services.audioPlayer;

import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.support.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;

import java.util.Scanner;

public class PlayerService {
    static Scanner sc = new Scanner(System.in);

    // Configurar ruta de VLC
    static {
        if (RuntimeUtil.isWindows()) {
            NativeLibrary.addSearchPath("libvlc", "C:\\Program Files\\VideoLAN\\VLC");
        } else {
            NativeLibrary.addSearchPath("libvlc", "/usr/lib/x86_64-linux-gnu");
        }
    }

    private final MediaPlayerFactory factory;
    private final MediaPlayer player;

    // Constructor
    public PlayerService() {

        factory = new MediaPlayerFactory();
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
