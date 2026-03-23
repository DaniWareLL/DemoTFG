package com.sonik.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonik.domain.model.Song;
import com.sonik.service.MetadataService;
import com.sonik.service.audio.AudioExtractor;

import java.util.List;

public class MetadataServiceImpl implements MetadataService {

    private final AudioExtractor extractor; // El puerto técnico
    private final String searchPrefix;

    public MetadataServiceImpl(AudioExtractor extractor, String searchPrefix) {
        this.extractor = extractor;
        this.searchPrefix = searchPrefix;
    }

    @Override
    public Song getMetadata(String searchPattern) throws Exception {
        String json = extractor.execute(List.of(
                "yt-dlp",
                "--dump-json",
                "--flat-playlist",
                searchPrefix + searchPattern
        ));

        JsonObject entry = JsonParser.parseString(json).getAsJsonObject();

        Song song = new Song();
        song.setTitle(entry.get("title").getAsString());
        song.setDurationSec(entry.get("duration").getAsInt());
        song.setOriginalUrl(entry.get("webpage_url").getAsString());
        song.setThumbnailUrl(entry.get("thumbnail").getAsString());

        return song;
    }
}