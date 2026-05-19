package com.sonik.domain.model.enums;

/**
 * Indicates the source
 */
public enum SourceName {

    YOUTUBE("ytsearch"),
    SOUNDCLOUD("scsearch");

    /**
     * The prefix to use alongside YtDlp
     */
    private final String searchPrefix;

    SourceName(String searchPrefix) {
        this.searchPrefix = searchPrefix;
    }

    public String getSearchPrefix() {
        return searchPrefix;
    }

}