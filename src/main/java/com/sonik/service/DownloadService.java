package com.sonik.service;


import com.sonik.domain.exceptions.AudioExtractorException;

/**
 * Handles the download system, what to do when downloading, where to save the song... etc.
 */
public interface DownloadService {

    /**
     *
     * @param url
     * @throws AudioExtractorException
     */
    public void downloadToMp3(String url) throws AudioExtractorException;
}
