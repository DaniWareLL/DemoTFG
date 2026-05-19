package com.sonik.domain.model.enums;

/**
 * Indicates the streaming quality
 */
public enum StreamingQuality {

    LOW("worstaudio"),
    MEDIUM("bestaudio[abr<=128]"),
    HIGH("bestaudio");

    /**
     * The prefix to use alongside YtDlp
     */
    private final String ytdlpFormat;

    StreamingQuality(String ytdlpFormat) {
        this.ytdlpFormat = ytdlpFormat;
    }

    public String getYtdlpFormat() {
        return ytdlpFormat;
    }


}