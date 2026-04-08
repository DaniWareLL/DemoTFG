package com.sonik;

import com.sonik.domain.model.Song;
import com.sonik.infrastructure.audio.VlcjAudioPlayer;
import com.sonik.infrastructure.audio.YtDlpClient;
import com.sonik.service.impl.DownloadServiceImpl;
import com.sonik.service.impl.MetadataServiceImpl;
import com.sonik.service.impl.PlayerServiceImpl;
import com.sonik.service.impl.SettingServiceImpl;

public class MainServices {
    static void main() throws Exception {

        final String searchPattern = "Bohemian Rhapsody - Queen";

        YtDlpClient client = new YtDlpClient();
        VlcjAudioPlayer audioPlayer = new VlcjAudioPlayer();

        String searchPrefix = "ytsearch:";
        String ffmpegPath = "ffmpeg-8.0.1-essentials_build/bin/ffmpeg.exe";

        SettingServiceImpl management = new SettingServiceImpl(client);
        System.out.println(management.getToolVersion());
        System.out.println(management.updateTool());

        PlayerServiceImpl streaming = new PlayerServiceImpl(client, searchPrefix);
        String[] StreamURL = streaming.getStreamUrl(searchPattern);

        MetadataServiceImpl metadata = new MetadataServiceImpl(client, searchPrefix);
        Song temp = metadata.getMetadata(searchPattern);
        System.out.println(temp);

        DownloadServiceImpl download = new DownloadServiceImpl(client, ffmpegPath, searchPrefix);
        download.downloadToMp3(searchPattern);

        audioPlayer.playStream(StreamURL[0]);



    }
}
