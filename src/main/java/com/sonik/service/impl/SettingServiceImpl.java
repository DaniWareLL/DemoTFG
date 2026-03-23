package com.sonik.service.impl;

import com.sonik.infrastructure.audio.YtDlpClient;
import com.sonik.service.SettingService;
import com.sonik.service.audio.AudioExtractor;

import java.util.List;

public class SettingServiceImpl implements SettingService {

    private final AudioExtractor audioExtractor;

    public SettingServiceImpl(AudioExtractor audioExtractor) {
        this.audioExtractor = audioExtractor;
    }

    @Override
    public String getToolVersion() throws Exception {
        return audioExtractor.execute(List.of("yt-dlp", "--version"));
    }

    @Override
    public String updateTool() throws Exception {
        return audioExtractor.execute(List.of("yt-dlp", "-U"));
    }
}
