package com.sonik.service.audio;

import com.sonik.domain.model.Song;

/**
 * Generic interface to easily change the audio playing technology
 */
public interface AudioPlayer {

    public void play(String streamUrl);
    public void resume();
    public void pause();
    public void stop();

    void setOnPlaying(Runnable callback);
    boolean isPlaying();

    public String getCurrentUrl();
    public Song getCurrentSong();

    public void setCurrentUrl(String currentUrl);
    public void setCurrentSong(Song currentSong);

    public int getCurrentTimeSec();

    public double getPosition();
    public void seekToPosition(double pos);

    void setVolume(int volume);
    int getVolume();

    void setMute(boolean mute);
    boolean isMute();

}
