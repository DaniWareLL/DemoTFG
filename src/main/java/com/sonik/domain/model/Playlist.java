package com.sonik.domain.model;

import com.sonik.domain.exceptions.IncorrectArgumentException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playlist_seq")
    @SequenceGenerator(
            name = "playlist_seq",
            sequenceName = "playlist_sequence",
            allocationSize = 1
    )
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder = 0;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaylistsSongs> songs = new ArrayList<>();

    public Playlist() {}

    public Playlist(User user, String name, String description,
                    LocalDate creationDate, int sortOrder) throws  IncorrectArgumentException {
        setUser(user);
        setName(name);
        setDescription(description);
        setCreationDate(creationDate);
        setSortOrder(sortOrder);
    }

    // Getters
    public int getId()                      { return id; }
    public User getUser()                   { return user; }
    public String getName()                 { return name; }
    public String getDescription()          { return description; }
    public LocalDate getCreationDate()      { return creationDate; }
    public int getSortOrder()               { return sortOrder; }
    public List<PlaylistsSongs> getSongs()  { return songs; }

    // Setters
    public void setUser(User user) throws IncorrectArgumentException {
        if (user == null)
            throw new IncorrectArgumentException(IncorrectArgumentException.NULL_OR_EMPTY_OBJECT);
        this.user = user;
    }

    public void setName(String name) throws IncorrectArgumentException {
        if (name == null || name.isBlank())
            throw new IncorrectArgumentException(IncorrectArgumentException.NULL_OR_EMPTY_OBJECT);
        this.name = name;
    }

    public void setDescription(String description) throws IncorrectArgumentException {
        if (description == null || description.isBlank()) {
            throw new IncorrectArgumentException(IncorrectArgumentException.NULL_OR_EMPTY_OBJECT);
        }
        this.description = description;
    }

    public void setCreationDate(LocalDate creationDate) throws IncorrectArgumentException {
        if (creationDate == null || creationDate.isAfter(LocalDate.now()))
            throw new IncorrectArgumentException(IncorrectArgumentException.INVALID_DATE);
        this.creationDate = creationDate;
    }

    public void setSortOrder(int sortOrder) throws IncorrectArgumentException {
        if (sortOrder < 0)
            throw new IncorrectArgumentException(IncorrectArgumentException.INVALID_NUMBER);
        this.sortOrder = sortOrder;
    }
}