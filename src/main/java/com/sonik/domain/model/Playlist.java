package com.sonik.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

/**
 * This is a placeholder, JPA entities are auto-generated
 */
@Entity
public class Playlist {
    @Id
    private Long id;
    private List<Song> songs;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    // Playlist data
}
