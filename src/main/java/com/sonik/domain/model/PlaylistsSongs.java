package com.sonik.domain.model;

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

    public PlaylistsSongs(Playlist playlist, Song song, int position, LocalDate addedAt) {
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
    public void setPlaylist(Playlist playlist) {
        if (playlist == null)
            throw new IllegalArgumentException("Playlist cannot be null");
        this.playlist = playlist;
    }

    public void setSong(Song song) {
        if (song == null)
            throw new IllegalArgumentException("Song cannot be null");
        this.song = song;
    }

    public void setPosition(int position) {
        if (position < 0)
            throw new IllegalArgumentException("Position cannot be negative");
        this.position = position;
    }

    public void setAddedAt(LocalDate addedAt) {
        if (addedAt == null)
            throw new IllegalArgumentException("Added date cannot be null");
        if (addedAt.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Added date cannot be in the future");
        this.addedAt = addedAt;
    }
}