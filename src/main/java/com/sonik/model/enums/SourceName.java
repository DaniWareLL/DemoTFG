package com.sonik.model.enums;

public enum SourceName {

    YOUTUBE("ytsearch:"),
    SOUNDCLOUD("scsearch:");

    private final String searchPrefix;

    SourceName(String searchPrefix) {
        this.searchPrefix = searchPrefix;
    }

    public String getSearchPrefix() {
        return searchPrefix;
    }

    public static SourceName fromString(String value) {
        return switch (value.toLowerCase()) {
            case "youtube" -> YOUTUBE;
            case "soundcloud" -> SOUNDCLOUD;
            default -> throw new IllegalArgumentException(
                    "Plataforma no soportada: " + value
            );
        };
    }
}