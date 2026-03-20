package com.sonik.infrastructure.streaming;

/**
 * The audio source used when the song is stored locally
 */
public class LocalAudioSource extends BaseAudioSource {

    public LocalAudioSource(AudioSource nextSource) {
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
