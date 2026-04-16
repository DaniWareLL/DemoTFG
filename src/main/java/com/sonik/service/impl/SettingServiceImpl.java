package com.sonik.service.impl;

import com.sonik.config.AppConfig;
import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.service.SettingService;
import com.sonik.service.audio.AudioExtractor;

import java.util.List;

public class SettingServiceImpl implements SettingService {

    private final AudioExtractor audioExtractor;

    public SettingServiceImpl(AudioExtractor audioExtractor) {
        this.audioExtractor = audioExtractor;
    }

    @Override
    public String getToolVersion() throws AudioExtractorException {
        try {
            return audioExtractor.execute(List.of(AppConfig.YTDLP_PATH, "--version"));
        } catch (Exception e) {
            throw new AudioExtractorException(AudioExtractorException.SETTING_ERROR, e);
        }
    }

    @Override
    public String updateTool() throws AudioExtractorException {
        try {
            return audioExtractor.execute(List.of(AppConfig.YTDLP_PATH, "-U"));
        } catch (Exception e) {
            throw new AudioExtractorException(AudioExtractorException.SETTING_ERROR, e);
        }
    }
}
