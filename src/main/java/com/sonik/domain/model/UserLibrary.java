package com.sonik.domain.model;

import com.sonik.domain.exceptions.IncorrectArgumentException;
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

    public UserLibrary(User user, Song song, LocalDate addedAt) throws IncorrectArgumentException {
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
    public void setUser(User user) throws IncorrectArgumentException {
        if (user == null)
            throw new IncorrectArgumentException(IncorrectArgumentException.NULL_OR_EMPTY_OBJECT);
        this.user = user;
    }

    public void setSong(Song song) throws IncorrectArgumentException {
        if (song == null)
            throw new IncorrectArgumentException(IncorrectArgumentException.NULL_OR_EMPTY_OBJECT);
        this.song = song;
    }

    public void setAddedAt(LocalDate addedAt) throws IncorrectArgumentException {
        if (addedAt == null || addedAt.isAfter(LocalDate.now()))
            throw new IncorrectArgumentException(IncorrectArgumentException.INVALID_DATE);
        this.addedAt = addedAt;
    }
}