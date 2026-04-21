    package com.sonik.service.impl;

    import com.sonik.config.AppConfig;
    import com.sonik.config.UserSession;
    import com.sonik.domain.exceptions.AudioExtractorException;
    import com.sonik.service.DownloadService;
    import com.sonik.service.audio.AudioExtractor;

    import java.util.List;

    /**
     * Implementation of the {@link DownloadService Download Service}
     */
    public class DownloadServiceImpl implements DownloadService {

        private final AudioExtractor extractor;
        private final String searchPrefix;
        String downloadDir = UserSession.getPreferences().getDownloadLocation();

        public DownloadServiceImpl(AudioExtractor extractor, String searchPrefix) {
            this.extractor = extractor;
            this.searchPrefix = searchPrefix;
        }

        @Override
        public void downloadToMp3(String searchPattern) throws AudioExtractorException {
            try {
                extractor.execute(List.of(
                        AppConfig.YTDLP_PATH,
                        "-f",                "bestaudio",
                        "--extract-audio",
                        "--audio-format",    "mp3",
                        "--audio-quality",   "0",
                        "--add-metadata",
                        "--embed-thumbnail",
                        "--convert-thumbnails", "jpg",
                        "--ffmpeg-location", AppConfig.FFMPEG_PATH,
                        "-P", downloadDir,
                        searchPrefix + searchPattern
                ));
            } catch (Exception e) {
                throw new AudioExtractorException(AudioExtractorException.DOWNLOAD_ERROR, e);
            }
        }
    }
