package com.sonik.service.impl;

import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.service.DownloadService;
import com.sonik.service.audio.AudioExtractor;

import java.util.List;

/**
 * Implementation of the {@link DownloadService Download Service}
 */
public class DownloadServiceImpl implements DownloadService {

    private final AudioExtractor extractor;
    private final String ffmpegPath;
    private final String searchPrefix;

    public DownloadServiceImpl(AudioExtractor extractor, String ffmpegPath, String searchPrefix) {
        this.extractor = extractor;
        this.ffmpegPath = ffmpegPath;
        this.searchPrefix = searchPrefix;
    }

    @Override
    public void downloadToMp3(String searchPattern) throws AudioExtractorException {
        try {
            extractor.execute(List.of(
                    "yt-dlp",
                    "-f",                "bestaudio",
                    "--extract-audio",
                    "--audio-format",    "mp3",
                    "--audio-quality",   "0",
                    "--add-metadata",
                    "--embed-thumbnail",
                    "--convert-thumbnails", "jpg",
                    "--ffmpeg-location", ffmpegPath,
                    searchPrefix + searchPattern
            ));
        } catch (Exception e) {
            throw new AudioExtractorException(AudioExtractorException.DOWNLOAD_ERROR, e);
        }
    }
}
