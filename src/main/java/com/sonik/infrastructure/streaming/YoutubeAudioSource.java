package com.sonik.infrastructure.streaming;

/**
 * Audio source to fetch songs from YouTube (using yt-dlp)
 */
public class YoutubeAudioSource extends BaseAudioSource {

    public YoutubeAudioSource(AudioSource nextSource) {
        super(nextSource);
    }

    @Override
    protected boolean canHandle(String request) {
        return false;
    }

    @Override
    protected void process(String request) {

    }
}
