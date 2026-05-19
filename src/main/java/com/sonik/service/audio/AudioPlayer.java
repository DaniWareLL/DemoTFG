package com.sonik.service.audio;

import com.sonik.domain.model.Song;

/**
 * Generic interface to easily change the audio playing technology
 */
public interface AudioPlayer {

    void play(String streamUrl);
    void resume();
    void pause();
    void stop();

    void setOnPlaying(Runnable callback);
    boolean isPlaying();

    String getCurrentUrl();
    Song getCurrentSong();

    void setCurrentUrl(String currentUrl);
    void setCurrentSong(Song currentSong);

    int getCurrentTimeSec();

    double getPosition();
    void seekToPosition(double pos);

    void setVolume(int volume);
    int getVolume();

    void setMute(boolean mute);
    boolean isMute();

}
