package com.sonik.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonik.config.AppConfig;
import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.model.Song;
import com.sonik.service.MetadataService;
import com.sonik.service.audio.AudioExtractor;

import java.util.ArrayList;
import java.util.List;

public class MetadataServiceImpl implements MetadataService {

    private final AudioExtractor extractor; // El puerto técnico

    public MetadataServiceImpl(AudioExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public List<Song> getMetadata(String searchPattern) throws AudioExtractorException {

        String searchPrefix = UserSession.getPreferences().getAudioSource().getSearchPrefix();
        try {
            String json = extractor.execute(List.of(
                    AppConfig.getYTDLPPath(),
                    "--flat-playlist",
                    "-J",
                    searchPrefix +"20:" + searchPattern
            ));

            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            JsonArray entriesArray = root.getAsJsonArray("entries");

            List<Song> songs = new ArrayList<>();
            for (int i = 0; i < entriesArray.size(); i++) {
                JsonObject entry = entriesArray.get(i).getAsJsonObject();

                Song metadata = new Song();
                metadata.setTitle(entry.get("title").getAsString());
                metadata.setDurationSec(entry.get("duration").getAsInt());
                metadata.setOriginalUrl(entry.get("url").getAsString());
                if(searchPrefix.contains("yt")) {
                    metadata.setSource("YouTube");
                } else {
                    metadata.setSource("SoundCloud");
                }


                if (entry.has("thumbnails") && entry.getAsJsonArray("thumbnails").size() > 0) {
                    JsonArray thumbnails = entry.getAsJsonArray("thumbnails");
                    JsonObject thumb = thumbnails.get(thumbnails.size() - 1).getAsJsonObject();
                    String rawUrl = thumb.get("url").getAsString();

                    // Limpiar URL removiendo parámetros
                    String cleanUrl = rawUrl.split("\\?")[0];
                    metadata.setThumbnailUrl(cleanUrl);
                }

                songs.add(metadata);
            }

            return songs;
        } catch (Exception e) {
            throw new AudioExtractorException(AudioExtractorException.METADATA_ERROR, e);
        }
    }
}