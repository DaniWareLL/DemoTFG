package com.sonik;

import com.sonik.config.AppConfig;
import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.model.Song;
import com.sonik.domain.model.User;
import com.sonik.domain.model.UserPref;
import com.sonik.domain.model.enums.SourceName;
import com.sonik.domain.model.enums.StreamingQuality;
import com.sonik.infrastructure.audio.VlcjAudioPlayer;
import com.sonik.infrastructure.audio.YtDlpClient;
import com.sonik.service.impl.DownloadServiceImpl;
import com.sonik.service.impl.MetadataServiceImpl;
import com.sonik.service.impl.PlayerServiceImpl;
import com.sonik.service.impl.SettingServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainServices {

    static void main() throws DataAccessException, IncorrectArgumentException {

        AppContext.initializeApplication();
        User u = new User("u","example@gmail.com","u", LocalDate.now());
        UserSession.setUser(u);
        UserPref up = new  UserPref(u, StreamingQuality.HIGH, UserPref.InterfaceTheme.DARK, SourceName.YOUTUBE);
        UserSession.setPreferences(up);

        String searchPattern = "Hey hola";

        YtDlpClient client = new YtDlpClient();
        VlcjAudioPlayer audioPlayer = new VlcjAudioPlayer();

        SourceName searchPrefix = SourceName.YOUTUBE;

//        SettingServiceImpl management = new SettingServiceImpl(client);
//        System.out.println(management.getToolVersion());
//        System.out.println(management.updateTool());

        PlayerServiceImpl streaming = new PlayerServiceImpl(client);
        String StreamURL = streaming.getStreamUrl(searchPattern);

        MetadataServiceImpl metadata = new MetadataServiceImpl(client);

        List<Song> songs = metadata.getMetadata(searchPattern);

        for (Song song : songs) {
            System.out.println(song);
        }

        DownloadServiceImpl download = new DownloadServiceImpl(client);
        download.downloadToMp3(searchPattern);

        audioPlayer.play(StreamURL);

    }
}