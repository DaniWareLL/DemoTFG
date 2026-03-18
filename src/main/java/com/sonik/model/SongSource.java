package com.sonik.model;

import jakarta.persistence.*;

@Entity
@Table(name = "song_source")
public class SongSource {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_source_seq")
    @SequenceGenerator(
            name = "song_source_seq",
            sequenceName = "song_source_sequence",
            allocationSize = 1
    )
    private int id;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_name", nullable = false)
    private SourceName sourceName;

    @Column(name = "priority", nullable = false)
    private int priority = 1;

    @Column(name = "path", length = 500)
    private String path;

    // ENUM interno
    public enum SourceName { YOUTUBE, SOUNDCLOUD, LOCAL }

    public SongSource() {}

    public SongSource(Song song, SourceName sourceName, int priority, String path) {
        setSong(song);
        setSourceName(sourceName);
        setPriority(priority);
        setPath(path);
    }

    // Getters
    public int getId()              { return id; }
    public Song getSong()           { return song; }
    public SourceName getSourceName(){ return sourceName; }
    public int getPriority()        { return priority; }
    public String getPath()         { return path; }

    // Setters
    public void setSong(Song song) {
        if (song == null)
            throw new IllegalArgumentException("Song cannot be null");
        this.song = song;
    }

    public void setSourceName(SourceName sourceName) {
        if (sourceName == null)
            throw new IllegalArgumentException("Source name cannot be null");
        this.sourceName = sourceName;
    }

    public void setPriority(int priority) {
        if (priority < 1)
            throw new IllegalArgumentException("Priority must be at least 1");
        this.priority = priority;
    }

    public void setPath(String path) {
        this.path = path;
    }
}