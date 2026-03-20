package com.sonik.infrastructure.streaming;

/**
 * Audio source to fetch songs from <a href="https://archive.org/">the Internet Archive</a>
 */
public class ArchiveOrgAudioSource extends BaseAudioSource {

    public ArchiveOrgAudioSource(AudioSource nextSource) {
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
