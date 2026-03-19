package com.sonik.services.metadata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonik.model.Song;
import com.sonik.infrastructure.ytDlp.YtDlpClient;

import java.util.List;

public class MetadataService {

    private final YtDlpClient client;
    private final String searchPrefix;

    public MetadataService(YtDlpClient client, String searchPrefix) {
        this.client       = client;
        this.searchPrefix = searchPrefix;
    }

    /**
     * Obtiene los metadatos de un video usando yt-dlp con el flag -J (JSON completo).
     *
     * @param searchPattern URL o nombre de la canción
     * @return Song con los metadatos extraídos
     */
    public Song getMetadata(String searchPattern) throws Exception {
        String json = client.execute(List.of(
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