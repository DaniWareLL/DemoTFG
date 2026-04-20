package com.sonik.service.impl;

import com.sonik.config.AppConfig;
import com.sonik.domain.exceptions.AudioExtractorException;
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
    @Override
    public String[] getStreamUrl(String searchPattern) throws AudioExtractorException {
        try {
            String result = extractor.execute(List.of(
                    AppConfig.YTDLP_PATH,
                    "-f", "bestaudio",
                    "--get-url",
                    searchPrefix + searchPattern
            ));

            if (result == null || result.isBlank()) {
                throw new AudioExtractorException(AudioExtractorException.STREAM_URL_ERROR);
            }

            // yt-dlp puede devolver múltiples líneas, nos quedamos con la última
            return result.split("\n");

        } catch (Exception e) {
            throw new AudioExtractorException(AudioExtractorException.STREAM_URL_ERROR, e);
        }
    }
}
