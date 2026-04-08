package com.sonik.service.impl;

import com.sonik.service.PlayerService;
import com.sonik.service.audio.AudioExtractor;

import java.util.List;


/**
 * Implementation of the {@link PlayerService Player Service}
 */
public class PlayerServiceImpl implements PlayerService {
    private final AudioExtractor extractor;
    private final String searchPrefix;

    public PlayerServiceImpl(AudioExtractor extractor, String searchPrefix) {
        this.extractor = extractor;
        this.searchPrefix = searchPrefix;
    }

    /**
     * Obtiene la URL directa del stream de audio usando yt-dlp.
     * El enlace firmado caduca en minutos.
     *
     * @param searchPattern nombre de la canción
     * @return URL directa del stream de audio
     */
    public String[] getStreamUrl(String searchPattern) throws Exception {
        String result = extractor.execute(List.of(
                "yt-dlp",
                "-f",        "bestaudio",
                "--get-url",
                searchPrefix + searchPattern
        ));

        System.out.println(result);

        if (result == null || result.isBlank()) {
            throw new RuntimeException("No se pudo obtener la URL del audio");
        }

        // yt-dlp puede devolver múltiples líneas, nos quedamos con la última
        return result.split("\n");
    }
}
