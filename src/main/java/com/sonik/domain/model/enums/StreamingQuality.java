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

    public static StreamingQuality fromDisplay(String display) {
        return switch (display) {
            case "Baja" -> LOW;
            case "Media" -> MEDIUM;
            case "Alta" -> HIGH;
            default -> throw new IllegalArgumentException("Valor no válido: " + display);
        };
    }

}