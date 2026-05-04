package com.sonik.service.audio;

/**
 * Generic interface to easily change the audio playing technology
 */
public interface AudioPlayer {
    public void play(String streamUrl);
    public void pause(String streamUrl);
    public void stop(String streamUrl);
}
