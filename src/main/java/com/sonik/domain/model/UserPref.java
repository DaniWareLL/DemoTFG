package com.sonik.domain.model;

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

    // ENUMs internos
    public enum StreamingQuality { LOW, MEDIUM, HIGH }
    public enum InterfaceTheme   { LIGHT, DARK }

    public UserPref() {}

    public UserPref(User user, StreamingQuality streamingQuality, InterfaceTheme interfaceTheme) {
        setUser(user);
        setStreamingQuality(streamingQuality);
        setInterfaceTheme(interfaceTheme);
    }

    // Getters
    public int getId()                          { return id; }
    public User getUser()                       { return user; }
    public StreamingQuality getStreamingQuality(){ return streamingQuality; }
    public InterfaceTheme getInterfaceTheme()   { return interfaceTheme; }

    // Setters
    public void setUser(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        this.user = user;
    }

    public void setStreamingQuality(StreamingQuality streamingQuality) {
        if (streamingQuality == null) throw new IllegalArgumentException("Streaming quality cannot be null");
        this.streamingQuality = streamingQuality;
    }

    public void setInterfaceTheme(InterfaceTheme interfaceTheme) {
        if (interfaceTheme == null) throw new IllegalArgumentException("Interface theme cannot be null");
        this.interfaceTheme = interfaceTheme;
    }
}