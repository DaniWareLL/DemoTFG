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

    /**
     * Creates a UserLibrary entry.
     * @param user    The User who owns this entry
     * @param song    The Song added to the library
     * @param addedAt The date the song was added
     * @throws IncorrectArgumentException <ul>
     *     <li>{@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If user or song is null</li>
     *     <li>{@link IncorrectArgumentException.ErrorType#INVALID_DATE} If addedAt is null or in the future</li>
     * </ul>
     */
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
    /**
     * Sets the user who owns this library entry.
     * @param user The User to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If user is null
     */
    public void setUser(User user) throws IncorrectArgumentException {
        if (user == null)
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.NULL_OBJECT_RECEIVED);
        this.user = user;
    }

    /**
     * Sets the song of this library entry.
     * @param song The Song to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If song is null
     */
    public void setSong(Song song) throws IncorrectArgumentException {
        if (song == null)
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.NULL_OBJECT_RECEIVED);
        this.song = song;
    }

    /**
     * Sets the date this song was added to the library.
     * @param addedAt The date to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#INVALID_DATE} If addedAt is null or in the future
     */
    public void setAddedAt(LocalDate addedAt) throws IncorrectArgumentException {
        if (addedAt == null || addedAt.isAfter(LocalDate.now()))
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.INVALID_DATE);
        this.addedAt = addedAt;
    }
}