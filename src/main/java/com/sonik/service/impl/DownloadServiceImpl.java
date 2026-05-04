    package com.sonik.service.impl;

    import com.sonik.config.AppConfig;
    import com.sonik.config.UserSession;
    import com.sonik.domain.exceptions.AudioExtractorException;
    import com.sonik.service.DownloadService;
    import com.sonik.service.audio.AudioExtractor;

    import java.io.IOException;
    import java.util.List;

    /**
     * Implementation of the {@link DownloadService Download Service}
     */
    public class DownloadServiceImpl implements DownloadService {

        private final AudioExtractor extractor;

        public DownloadServiceImpl(AudioExtractor extractor) {
            this.extractor = extractor;
        }

        @Override
        public void downloadToMp3(String url) throws AudioExtractorException {

            String downloadDir = UserSession.getPreferences().getDownloadLocation();
            String quality = UserSession.getPreferences().getStreamingQuality().getYtdlpFormat();
            try {
                extractor.execute(List.of(
                        AppConfig.getYTDLPPath(),
                        "-f", quality,
                        "--extract-audio",
                        "--audio-format", "mp3",
                        "--audio-quality", "0",
                        "--add-metadata",
                        "--embed-thumbnail",
                        "--convert-thumbnails", "jpg",
                        "--ffmpeg-location", AppConfig.getFFmpegPath(),
                        "-P", downloadDir,
                        url
                ));
            } catch (IOException | InterruptedException e) {
                throw new AudioExtractorException(AudioExtractorException.DOWNLOAD_ERROR, e);
            }
        }
    }
