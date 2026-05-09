package com.sonik.ui.navigation;

import java.net.URL;

/**
 * Contains all the different types of views
 */
public enum ViewType {

    // Main Side
    SIGN_IN("/views/signin-view.fxml"),
    SIGN_UP("/views/signup-view.fxml"),
    HOME("/views/home-view.fxml"),
    SEARCH("/views/search-view.fxml"),
    SETTINGS("/views/settings-view.fxml"),
    PLAYLIST("/views/playlist-view.fxml"),
    ERROR_WINDOW("/views/error-window.fxml"),
    SONG_OPTIONS("/views/playlist-selector-menu.fxml"),

    // Left Side
    PLAYLIST_SIDEBAR("/views/playlist-sidebar-view.fxml"),

    // Bottom Side
    PLAYER_BOTTOMBAR("/views/player-bottom-bar-view.fxml");

    private final String fxmlPath;

    ViewType(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public URL getUrl() {
        return ViewType.class.getResource(fxmlPath);
    }
}
