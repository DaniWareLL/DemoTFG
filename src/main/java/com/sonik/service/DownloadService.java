package com.sonik.service;


import com.sonik.domain.exceptions.AudioExtractorException;

/**
 * Handles the download system, what to do when downloading, where to save the song... etc.
 */
public interface DownloadService {

    /**
     *
     * @param searchPattern
     * @throws AudioExtractorException
     */
    public void downloadToMp3(String searchPattern) throws AudioExtractorException;
}
