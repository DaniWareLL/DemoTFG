package com.sonik.domain.model;

import com.sonik.domain.exceptions.IncorrectArgumentException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_seq")
    @SequenceGenerator(
            name = "song_seq",
            sequenceName = "song_sequence",
            allocationSize = 1
    )
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "duration_sec")
    private Integer durationSec;

    @Column(name = "original_url", length = 500)
    private String originalUrl;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(name = "aggregation_date", nullable = false)
    private LocalDate aggregationDate;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SongSource> sources = new ArrayList<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaylistsSongs> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLibrary> userLibraries = new ArrayList<>();

    public Song() {}

    public Song(String title, Integer durationSec, String originalUrl,
                String thumbnailUrl, LocalDate aggregationDate) throws IncorrectArgumentException {
        setTitle(title);
        setDurationSec(durationSec);
        setOriginalUrl(originalUrl);
        setThumbnailUrl(thumbnailUrl);
        setAggregationDate(aggregationDate);
    }

    // Getters
    public int getId()                          { return id; }
    public String getTitle()                    { return title; }
    public Integer getDurationSec()             { return durationSec; }
    public String getOriginalUrl()              { return originalUrl; }
    public String getThumbnailUrl()             { return thumbnailUrl; }
    public LocalDate getAggregationDate()       { return aggregationDate; }
    public List<SongSource> getSources()        { return sources; }
    public List<PlaylistsSongs> getPlaylists()  { return playlists; }
    public List<UserLibrary> getUserLibraries() { return userLibraries; }

    // Setters
    public void setTitle(String title) throws IncorrectArgumentException {
        if (title == null || title.isBlank())
            throw new IncorrectArgumentException(IncorrectArgumentException.NULL_OR_EMPTY_OBJECT);
        this.title = title;
    }

    public void setDurationSec(Integer durationSec) throws IncorrectArgumentException {
        if (durationSec != null && durationSec < 0)
            throw new IncorrectArgumentException(IncorrectArgumentException.INVALID_NUMBER);
        this.durationSec = durationSec;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setAggregationDate(LocalDate aggregationDate) throws IncorrectArgumentException {
        if (aggregationDate == null || aggregationDate.isAfter(LocalDate.now()))
            throw new IncorrectArgumentException(IncorrectArgumentException.INVALID_DATE);
        this.aggregationDate = aggregationDate;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", durationSec=" + durationSec +
                ", originalUrl='" + originalUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", aggregationDate=" + aggregationDate +
                ", sources=" + sources +
                ", playlists=" + playlists +
                ", userLibraries=" + userLibraries +
                '}';
    }
}