package com.sonik.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * This is a placeholder, JPA entities are auto-generated
 */
@Entity
public class Song {
    @Id
    private Long id;
    private String artist;
    private String durationSeconds;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    // Song data
}
