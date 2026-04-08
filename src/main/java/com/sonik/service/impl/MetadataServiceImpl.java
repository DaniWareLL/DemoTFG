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
                "-J",
                searchPrefix + searchPattern
        ));

        JsonObject root  = JsonParser.parseString(json).getAsJsonObject();
        JsonObject entry = root.getAsJsonArray("entries")
                .get(0)
                .getAsJsonObject();

        Song metadata = new Song();
        metadata.setTitle(entry.get("title").getAsString());
        metadata.setDurationSec(entry.get("duration").getAsInt());
        metadata.setOriginalUrl(entry.get("webpage_url").getAsString());
        metadata.setThumbnailUrl(entry.get("thumbnail").getAsString());

        return metadata;
    }
}