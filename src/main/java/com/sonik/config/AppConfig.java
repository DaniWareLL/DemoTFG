package com.sonik.config;

import com.sonik.domain.exceptions.DataAccessException;
import com.sun.jna.NativeLibrary;
import org.apache.commons.lang3.SystemUtils;

import java.nio.file.Paths;

/**
 * This class contains the application constants.<br>
 * Each constant has to be initialized in the {@link #initializeContext()} method, and accessed only through its static {@code get} method.
 */
public class AppConfig {

    private static String FFMPEG_PATH;

    private static String YTDLP_PATH;

    private static String VLC_PATH;

    /**
     * Prevents default values from being overwritten
     */
    private static boolean alreadyStarted = false;

    /**
     * This method can only be used once (ideally on startup)
     * @throws DataAccessException When this method is called after having been called already
     */
    protected static void initializeContext() throws DataAccessException {

        if (alreadyStarted) {
            throw new DataAccessException(DataAccessException.ALREADY_CONFIGURED);
        }
        if (SystemUtils.IS_OS_WINDOWS) {
            VLC_PATH = Paths.get("bin", "libvlc-win").toAbsolutePath().toString();
            FFMPEG_PATH = Paths.get("bin", "ffmpeg.exe").toAbsolutePath().toString();
            YTDLP_PATH = Paths.get("bin", "yt-dlp.exe").toAbsolutePath().toString();
        } else {
            VLC_PATH = Paths.get("bin", "libvlc-linux").toAbsolutePath().toString();
            FFMPEG_PATH = Paths.get("bin", "ffmpeg_linux").toAbsolutePath().toString();
            YTDLP_PATH = Paths.get("bin", "yt-dlp_linux").toAbsolutePath().toString();
        }
        alreadyStarted = true;
    }

    public static String getVlcPath() {
        return VLC_PATH;
    }

    public static String getFFmpegPath() {
        return FFMPEG_PATH;
    }

    public static String getYTDLPPath() {
        return YTDLP_PATH;
    }

}