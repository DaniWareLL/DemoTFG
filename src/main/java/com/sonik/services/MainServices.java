package com.sonik.services;

import com.sonik.model.Song;
import com.sonik.services.audioPlayer.PlayerService;
import com.sonik.services.download.DownloadService;
import com.sonik.services.management.ManagementService;
import com.sonik.services.metadata.MetadataService;
import com.sonik.services.streaming.StreamingService;
import com.sonik.infrastructure.ytDlp.YtDlpClient;

public class MainServices {
    static void main() throws Exception {

        final String searchPattern = "Bohemian Rhapsody - Queen";

        YtDlpClient client = new YtDlpClient();
        String searchPrefix = "ytsearch:";
        String ffmpegPath = "ffmpeg-8.0.1-essentials_build/bin/ffmpeg.exe";

        ManagementService management = new ManagementService(client);
        StreamingService streaming = new StreamingService(client, searchPrefix);
        MetadataService metadata = new MetadataService(client, searchPrefix);
        DownloadService download = new DownloadService(client, ffmpegPath, searchPrefix);
        PlayerService player = new PlayerService();

        management.getVersion();
        management.update();
        String[] StreamURL = streaming.getStreamUrl(searchPattern);
        Song temp = metadata.getMetadata(searchPattern);
        System.out.println(temp);
        download.downloadMp3(searchPattern);
        player.playStream(StreamURL[0]);



    }
}