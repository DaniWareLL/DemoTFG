package com.sonik;

import com.sonik.domain.model.Song;
import com.sonik.domain.model.enums.SourceName;
import com.sonik.infrastructure.audio.VlcjAudioPlayer;
import com.sonik.infrastructure.audio.YtDlpClient;
import com.sonik.service.impl.DownloadServiceImpl;
import com.sonik.service.impl.MetadataServiceImpl;
import com.sonik.service.impl.PlayerServiceImpl;
import com.sonik.service.impl.SettingServiceImpl;

public class MainServices {
    static void main() {

        final String searchPattern = "Bohemian Rhapsody - Queen";

        YtDlpClient client = new YtDlpClient();
        VlcjAudioPlayer audioPlayer = new VlcjAudioPlayer();

        String searchPrefix = SourceName.fromString("youtube").getSearchPrefix();

        SettingServiceImpl management = new SettingServiceImpl(client);
        System.out.println(management.getToolVersion());
        System.out.println(management.updateTool());

        PlayerServiceImpl streaming = new PlayerServiceImpl(client, searchPrefix);
        String[] StreamURL = streaming.getStreamUrl(searchPattern);

        MetadataServiceImpl metadata = new MetadataServiceImpl(client, searchPrefix);
        Song temp = metadata.getMetadata(searchPattern);
        System.out.println(temp);

        DownloadServiceImpl download = new DownloadServiceImpl(client, searchPrefix);
        download.downloadToMp3(searchPattern);

        audioPlayer.playStream(StreamURL[0]);

    }
}
