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

    @Column(name = "original_url", unique = true, length = 500)
    private String originalUrl;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(name = "aggregation_date", nullable = false)
    private LocalDate aggregationDate;

    @Column(name = "source_name", nullable = false)
    private String source;

    @Column(name ="download_path", nullable = true)
    private String downloadPath;

    /*
     * We don't use cascade = CascadeType.ALL because Song is an aggregate root.
     * UserLibrary depends on Song, not the other way around.
     */
    @OneToMany(mappedBy = "song")
    private List<PlaylistsSongs> playlists = new ArrayList<>();

    /*
     * Same as PlaylistsSongs
     */
    @OneToMany(mappedBy = "song")
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
    public int getId() { return id; }
    public String getTitle() { return title; }
    public Integer getDurationSec() { return durationSec; }
    public String getOriginalUrl() { return originalUrl; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public LocalDate getAggregationDate() { return aggregationDate; }
    //public List<SongSource> getSources()        { return sources; }
    public List<PlaylistsSongs> getPlaylists() { return playlists; }
    public List<UserLibrary> getUserLibraries() { return userLibraries; }
    public String getSource() { return source;}
    public String getDownloadPath() { return downloadPath; }


    // Setters
    /**
     * Sets the title of this song.
     * @param title The title to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If title is null or blank
     */
    public void setTitle(String title) throws IncorrectArgumentException {
        if (title == null || title.isBlank())
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.NULL_OBJECT_RECEIVED);
        this.title = title;
    }

    /**
     * Sets the duration of this song in seconds.
     * @param durationSec The duration to assign in seconds, must be zero or positive. Can be null if unknown.
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#INVALID_NUMBER} If durationSec is negative
     */
    public void setDurationSec(Integer durationSec) throws IncorrectArgumentException {
        if (durationSec != null && durationSec < 0)
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.INVALID_NUMBER);
        this.durationSec = durationSec;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * Sets the aggregation date of this song.
     * @param aggregationDate The date to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#INVALID_DATE} If aggregationDate is null or in the future
     */
    public void setAggregationDate(LocalDate aggregationDate) throws IncorrectArgumentException {
        if (aggregationDate == null || aggregationDate.isAfter(LocalDate.now()))
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.INVALID_DATE);
        this.aggregationDate = aggregationDate;
    }

    /**
     * Sets the source platform of this song (e.g. YouTube, SoundCloud).
     * @param source The source to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#INVALID_SOURCE} If source is null or blank
     */
    public void setSource(String source) throws IncorrectArgumentException {
        if (source == null || source.isBlank()) throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.INVALID_SOURCE);
        this.source = source;
    }

    /**
     * Sets the local download path of this song.
     * @param downloadPath The path to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#INVALID_DOWNLOAD_PATH} If downloadPath is null or blank
     */
    public void setDownloadPath(String downloadPath) throws IncorrectArgumentException {
        if (downloadPath == null || downloadPath.isBlank()) throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.INVALID_DOWNLOAD_PATH);
        this.downloadPath = downloadPath;
    }

    public void setPlaylists(List<PlaylistsSongs> playlists) {
        this.playlists = playlists;
    }

    public void setUserLibraries(List<UserLibrary> userLibraries) {
        this.userLibraries = userLibraries;
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
                '}';
    }
}