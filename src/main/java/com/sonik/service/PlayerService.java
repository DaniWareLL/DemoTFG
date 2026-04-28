package com.sonik.service;

import com.sonik.domain.exceptions.AudioExtractorException;

/**
 * Handles the song player, which songs to play and mainly song control buttons, also the queue
 */
public interface PlayerService {

    String[] getStreamUrl(String searchPattern) throws AudioExtractorException;


}
