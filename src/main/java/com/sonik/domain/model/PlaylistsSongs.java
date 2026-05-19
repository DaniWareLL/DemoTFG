package com.sonik.domain.model;

import com.sonik.domain.exceptions.IncorrectArgumentException;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "playlists_songs")
public class PlaylistsSongs {

    @EmbeddedId
    private PlaylistsSongsId id;

    @ManyToOne
    @MapsId("playlistId")
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    private Song song;

    @Column(name = "added_at", nullable = false)
    private LocalDate addedAt;

    public PlaylistsSongs() {}

    public PlaylistsSongs(Playlist playlist, Song song, LocalDate addedAt) throws IncorrectArgumentException {
        setId(playlist, song);
        setPlaylist(playlist);
        setSong(song);
        setAddedAt(addedAt);
    }

    // Getters
    public PlaylistsSongsId getId() { return id; }
    public Playlist getPlaylist()   { return playlist; }
    public Song getSong()           { return song; }
    public LocalDate getAddedAt()   { return addedAt; }

    // Setters

    /**
     * Sets the composite ID of this entry from the given playlist and song.
     * @param playlist The Playlist to extract the ID from
     * @param song     The Song to extract the ID from
     */
    public void setId(Playlist playlist, Song song) {
        this.id = new PlaylistsSongsId(playlist.getId(), song.getId());
    }

    /**
     * Sets the playlist of this entry.
     * @param playlist The Playlist to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If playlist is null
     */
    public void setPlaylist(Playlist playlist) throws IncorrectArgumentException {
        if (playlist == null)
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.NULL_OBJECT_RECEIVED);
        this.playlist = playlist;
    }

    /**
     * Sets the song of this entry.
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
     * Sets the date this song was added to the playlist.
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