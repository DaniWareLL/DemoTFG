package com.sonik.services.download;

import com.sonik.infrastructure.ytDlp.YtDlpClient;

import java.util.List;

public class DownloadService {

    private final YtDlpClient client;
    private final String ffmpegPath;
    private final String searchPrefix;

    public DownloadService(YtDlpClient client, String ffmpegPath, String searchPrefix) {
        this.client     = client;
        this.ffmpegPath = ffmpegPath;
        this.searchPrefix = searchPrefix;
    }

    /**
     *
     * @param searchPattern
     * @throws Exception
     */
    public void downloadMp3(String searchPattern) throws Exception {
        client.execute(List.of(
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
    }
}
