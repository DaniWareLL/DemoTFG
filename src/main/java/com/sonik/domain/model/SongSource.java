package com.sonik.domain.model;

import com.sonik.domain.exceptions.IncorrectArgumentException;
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

    /**
     * Creates a SongSource.
     * @param song       The Song this source belongs to
     * @param sourceName The platform this source comes from
     * @param priority   The priority of this source, must be 1 or greater
     * @param path       The local path to the downloaded file, can be null
     * @throws IncorrectArgumentException <ul>
     *     <li>{@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If song or sourceName is null</li>
     *     <li>{@link IncorrectArgumentException.ErrorType#INVALID_NUMBER} If priority is less than 1</li>
     * </ul>
     */
    public SongSource(Song song, SourceName sourceName, int priority, String path) throws IncorrectArgumentException {
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

    /**
     * Sets the song this source belongs to.
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
     * Sets the platform this source comes from.
     * @param sourceName The SourceName to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If sourceName is null
     */
    public void setSourceName(SourceName sourceName) throws IncorrectArgumentException {
        if (sourceName == null)
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.NULL_OBJECT_RECEIVED);
        this.sourceName = sourceName;
    }

    /**
     * Sets the priority of this source. Lower values are tried first when playing a song.
     * @param priority The priority to assign, must be 1 or greater
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#INVALID_NUMBER} If priority is less than 1
     */
    public void setPriority(int priority) throws IncorrectArgumentException {
        if (priority < 1)
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.INVALID_NUMBER);
        this.priority = priority;
    }

    public void setPath(String path) {
        this.path = path;
    }
}