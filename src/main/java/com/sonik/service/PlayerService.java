package com.sonik.service;

import com.sonik.domain.exceptions.AudioExtractorException;

/**
 * Handles the song player, which songs to play and mainly song control buttons, also the queue
 */
public interface PlayerService {

    /**
     * Retrieves a direct stream URL for the given song URL.
     * Quality is taken from the current user's session preferences.
     * @param url The URL of the song to stream
     * @return The direct stream URL
     * @throws AudioExtractorException If the URL cannot be retrieved or is blank
     */
    String getStreamUrl(String url) throws AudioExtractorException;
}
