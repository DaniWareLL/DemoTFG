package com.sonik.domain.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_library")
public class UserLibrary {

    @EmbeddedId
    private UserLibraryId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    private Song song;

    @Column(name = "added_at", nullable = false)
    private LocalDate addedAt;

    public UserLibrary() {}

    public UserLibrary(User user, Song song, LocalDate addedAt) {
        this.id = new UserLibraryId(user.getId(), song.getId());
        setUser(user);
        setSong(song);
        setAddedAt(addedAt);
    }

    // Getters
    public UserLibraryId getId() { return id; }
    public User getUser()        { return user; }
    public Song getSong()        { return song; }
    public LocalDate getAddedAt(){ return addedAt; }

    // Setters
    public void setUser(User user) {
        if (user == null)
            throw new IllegalArgumentException("User cannot be null");
        this.user = user;
    }

    public void setSong(Song song) {
        if (song == null)
            throw new IllegalArgumentException("Song cannot be null");
        this.song = song;
    }

    public void setAddedAt(LocalDate addedAt) {
        if (addedAt == null)
            throw new IllegalArgumentException("Added date cannot be null");
        if (addedAt.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Added date cannot be in the future");
        this.addedAt = addedAt;
    }
}