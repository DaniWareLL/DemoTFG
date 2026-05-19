package com.sonik.service;


import com.sonik.domain.exceptions.AudioExtractorException;

/**
 * Handles the download system, what to do when downloading, where to save the song... etc.
 */
public interface DownloadService {

    /**
     * Downloads a song from the given URL and converts it to MP3.
     * Output directory and quality are taken from the current user's session preferences.
     * @param url The URL of the song to download
     * @throws AudioExtractorException If the download or conversion fails
     */
    void downloadToMp3(String url) throws AudioExtractorException;
}
