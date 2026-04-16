package com.sonik.config;

import java.nio.file.Paths;

/**
 * This class contains the application constants such as the name, version and other important information.
 * It basically contains all(or most) of the application's default values.
 */
public class AppConfig {

    public static final String FFMPEG_PATH = Paths.get("bin", "ffmpeg.exe").toAbsolutePath().toString();

    public static final String YTDLP_PATH =  Paths.get("bin", "yt-dlp.exe").toAbsolutePath().toString();

    // it depends on the S.O
    // public static final String VLC_PATH = Paths.get("bin", "libvlc").toAbsolutePath().toString();



}
