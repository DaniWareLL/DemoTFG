package com.sonik.domain.model.enums;

public enum SourceName {

    YOUTUBE("ytsearch"),
    SOUNDCLOUD("scsearch");

    private final String searchPrefix;

    SourceName(String searchPrefix) {
        this.searchPrefix = searchPrefix;
    }

    public String getSearchPrefix() {
        return searchPrefix;
    }
    
}