package com.sonik.domain.model.enums;


public enum StreamingQuality {

    LOW("worstaudio"),
    MEDIUM("bestaudio[abr<=128]"),
    HIGH("bestaudio");

    private final String ytdlpFormat;

    StreamingQuality(String ytdlpFormat) {
        this.ytdlpFormat = ytdlpFormat;
    }

    public String getYtdlpFormat() {
        return ytdlpFormat;
    }
    

}