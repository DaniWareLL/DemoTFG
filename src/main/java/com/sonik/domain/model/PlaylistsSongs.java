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

    @Column(name = "position", nullable = false)
    private int position;

    @Column(name = "added_at", nullable = false)
    private LocalDate addedAt;

    public PlaylistsSongs() {}

    public PlaylistsSongs(Playlist playlist, Song song, int position, LocalDate addedAt) throws IncorrectArgumentException {
        this.id = new PlaylistsSongsId(playlist.getId(), song.getId());
        setPlaylist(playlist);
        setSong(song);
        setPosition(position);
        setAddedAt(addedAt);
    }

    // Getters
    public PlaylistsSongsId getId() { return id; }
    public Playlist getPlaylist()   { return playlist; }
    public Song getSong()           { return song; }
    public int getPosition()        { return position; }
    public LocalDate getAddedAt()   { return addedAt; }

    // Setters
    public void setPlaylist(Playlist playlist) throws IncorrectArgumentException {
        if (playlist == null)
            throw new IncorrectArgumentException(IncorrectArgumentException.NULL_OR_EMPTY_OBJECT);
        this.playlist = playlist;
    }

    public void setSong(Song song) throws IncorrectArgumentException {
        if (song == null)
            throw new IncorrectArgumentException(IncorrectArgumentException.NULL_OR_EMPTY_OBJECT);
        this.song = song;
    }

    public void setPosition(int position) throws IncorrectArgumentException {
        if (position < 0)
            throw new IncorrectArgumentException(IncorrectArgumentException.INVALID_NUMBER);
        this.position = position;
    }

    public void setAddedAt(LocalDate addedAt) throws IncorrectArgumentException {
        if (addedAt == null || addedAt.isAfter(LocalDate.now()))
            throw new IncorrectArgumentException(IncorrectArgumentException.INVALID_DATE);
        this.addedAt = addedAt;
    }
}