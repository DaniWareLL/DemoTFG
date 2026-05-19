package com.sonik.service.impl;

import com.sonik.config.AppConfig;
import com.sonik.config.AppContext;
import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.service.PlayerService;
import com.sonik.service.audio.AudioExtractor;

import java.util.List;


/**
 * Implementation of the {@link PlayerService Player Service}
 */
public class PlayerServiceImpl implements PlayerService {
    private final AudioExtractor extractor;

    public PlayerServiceImpl(AudioExtractor extractor) {
        this.extractor = extractor;
    }
    
    @Override
    public String getStreamUrl(String url) throws AudioExtractorException {

        String quality = UserSession.getPreferences().getStreamingQuality().getYtdlpFormat();

        try {
            String result = extractor.execute(List.of(
                    AppConfig.getYTDLPPath(),
                    "-f", quality,
                    "--get-url",
                    url
            ));

            if (result == null || result.isBlank()) {
                throw new AudioExtractorException(AudioExtractorException.STREAM_URL_ERROR);
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new AudioExtractorException(AudioExtractorException.STREAM_URL_ERROR, e);
        }
    }
}
