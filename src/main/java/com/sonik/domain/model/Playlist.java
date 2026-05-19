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

    // If a playlist is deleted, its relations will also be deleted
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PlaylistsSongs> songs = new ArrayList<>();

    public Playlist() {}

    /**
     * Creates a Playlist
     * @param user The User who owns the playlist
     * @param name The name of the playlist
     * @param description The description of the playlist
     * @param creationDate The creation date of the playlist
     * @throws IncorrectArgumentException <ul>
     *     <li>{@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If User is null</li>
     *     <li>{@link IncorrectArgumentException.ErrorType#EMPTY_PLAYLIST_NAME}</li>
     *     <li>{@link IncorrectArgumentException.ErrorType#EMPTY_PLAYLIST_DESCRIPTION}</li>
     *     <li>{@link IncorrectArgumentException.ErrorType#INVALID_DATE} If the creation date is in the future </li>
     * </ul>
     */
    public Playlist(User user, String name, String description,
                    LocalDate creationDate) throws  IncorrectArgumentException {
        setUser(user);
        setName(name);
        setDescription(description);
        setCreationDate(creationDate);
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
    /**
     * Sets the user who owns this playlist.
     * @param user The User to assign as owner
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#NULL_OBJECT_RECEIVED} If user is null
     */
    public void setUser(User user) throws IncorrectArgumentException {
        if (user == null)
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.NULL_OBJECT_RECEIVED);
        this.user = user;
    }

    /**
     * Sets the name of this playlist.
     * @param name The name to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#EMPTY_PLAYLIST_NAME} If name is null or blank
     */
    public void setName(String name) throws IncorrectArgumentException {
        if (name == null || name.isBlank())
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.EMPTY_PLAYLIST_NAME);
        this.name = name;
    }

    /**
     * Sets the description of this playlist.
     * @param description The description to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#EMPTY_PLAYLIST_DESCRIPTION} If description is null or blank
     */
    public void setDescription(String description) throws IncorrectArgumentException {
        if (description == null || description.isBlank()) {
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.EMPTY_PLAYLIST_DESCRIPTION);
        }
        this.description = description;
    }

    /**
     * Sets the creation date of this playlist.
     * @param creationDate The creation date to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#INVALID_DATE} If creationDate is null or in the future
     */
    public void setCreationDate(LocalDate creationDate) throws IncorrectArgumentException {
        if (creationDate == null || creationDate.isAfter(LocalDate.now()))
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.INVALID_DATE);
        this.creationDate = creationDate;
    }

    /**
     * Sets the sort order of this playlist.
     * @param sortOrder The sort order to assign, must be zero or positive
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#INVALID_NUMBER} If sortOrder is negative
     */
    public void setSortOrder(int sortOrder) throws IncorrectArgumentException {
        if (sortOrder < 0)
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.INVALID_NUMBER);
        this.sortOrder = sortOrder;
    }
}