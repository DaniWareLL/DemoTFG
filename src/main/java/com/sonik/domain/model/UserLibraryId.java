package com.sonik.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserLibraryId implements Serializable {

    private int userId;
    private int songId;

    public UserLibraryId() {}

    public UserLibraryId(int userId, int songId) {
        this.userId = userId;
        this.songId = songId;
    }

    public int getUserId() { return userId; }
    public int getSongId() { return songId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLibraryId)) return false;
        UserLibraryId that = (UserLibraryId) o;
        return userId == that.userId && songId == that.songId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, songId);
    }
}