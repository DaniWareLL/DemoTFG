package com.sonik.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlaylistsSongsId implements Serializable {

    private int playlistId;
    private int songId;

    public PlaylistsSongsId() {}

    public PlaylistsSongsId(int playlistId, int songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }

    public int getPlaylistId() { return playlistId; }
    public int getSongId()     { return songId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistsSongsId)) return false;
        PlaylistsSongsId that = (PlaylistsSongsId) o;
        return playlistId == that.playlistId && songId == that.songId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, songId);
    }
}
