package com.sonik.domain.model;

import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.model.enums.SourceName;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.model.enums.StreamingQuality;
import jakarta.persistence.*;

@Entity
@Table(name = "user_pref")
public class UserPref {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_pref_seq")
    @SequenceGenerator(
            name = "user_pref_seq",
            sequenceName = "user_pref_sequence",
            allocationSize = 1
    )
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "streaming_quality", nullable = false)
    private StreamingQuality streamingQuality = StreamingQuality.HIGH;

    @Enumerated(EnumType.STRING)
    @Column(name = "interface_theme", nullable = false)
    private InterfaceTheme interfaceTheme = InterfaceTheme.DARK;

    @Enumerated(EnumType.STRING)
    @Column(name = "audio_source", nullable = false)
    private SourceName audioSource = SourceName.YOUTUBE;

    @Column(name = "download_location", nullable = false)
    private String downloadLocation = System.getProperty("user.home") + "/Downloads";

    public enum InterfaceTheme   { LIGHT, DARK }

    public UserPref() {}

    /**
     * Creates a UserPref.
     * @param user             The User these preferences belong to
     * @param streamingQuality The streaming quality preference
     * @param interfaceTheme   The interface theme preference
     * @param audioSource      The preferred audio source
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If any parameter is null
     */
    public UserPref(User user, StreamingQuality streamingQuality, InterfaceTheme interfaceTheme,  SourceName audioSource) throws IncorrectArgumentException {
        setUser(user);
        setStreamingQuality(streamingQuality);
        setInterfaceTheme(interfaceTheme);
        setAudioSource(audioSource);
    }

    // Getters
    public int getId() { return id; }
    public User getUser() { return user; }
    public StreamingQuality getStreamingQuality() { return streamingQuality; }
    public InterfaceTheme getInterfaceTheme() { return interfaceTheme; }
    public SourceName getAudioSource() { return audioSource; }
    public String getDownloadLocation() { return downloadLocation; }

    // Setters
    /**
     * Sets the user these preferences belong to.
     * @param user The User to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If user is null
     */
    public void setUser(User user) throws IncorrectArgumentException {
        if (user == null) throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.NULL_OBJECT_RECEIVED);
        this.user = user;
    }

    /**
     * Sets the streaming quality preference.
     * @param streamingQuality The StreamingQuality to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If streamingQuality is null
     */
    public void setStreamingQuality(StreamingQuality streamingQuality) throws IncorrectArgumentException {
        if (streamingQuality == null) throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.NULL_OBJECT_RECEIVED);
        this.streamingQuality = streamingQuality;
    }

    /**
     * Sets the interface theme preference.
     * @param interfaceTheme The InterfaceTheme to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If interfaceTheme is null
     */
    public void setInterfaceTheme(InterfaceTheme interfaceTheme) throws IncorrectArgumentException {
        if (interfaceTheme == null) throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.NULL_OBJECT_RECEIVED);
        this.interfaceTheme = interfaceTheme;
    }

    /**
     * Sets the preferred audio source.
     * @param audioSource The SourceName to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If audioSource is null
     */
    public void setAudioSource(SourceName audioSource) throws IncorrectArgumentException {
        if (audioSource == null) throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.NULL_OBJECT_RECEIVED);
        this.audioSource = audioSource;
    }

    /**
     * Sets the download location for songs.
     * @param downloadLocation The path to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If downloadLocation is null
     */
    public void setDownloadLocation(String downloadLocation) throws IncorrectArgumentException {
        if (downloadLocation == null ) throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.NULL_OBJECT_RECEIVED);
        this.downloadLocation = downloadLocation;
    }
}