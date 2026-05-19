package com.sonik.service;

import com.sonik.domain.exceptions.AudioExtractorException;

public interface SettingService {

    /**
     * Returns the current version of yt-dlp.
     * @return The version string
     * @throws AudioExtractorException If the version cannot be retrieved
     */
    String getToolVersion() throws AudioExtractorException;

    /**
     * Updates yt-dlp to the latest version.
     * @return The output of the update command
     * @throws AudioExtractorException If the update fails
     */
    String updateTool() throws AudioExtractorException;

}
