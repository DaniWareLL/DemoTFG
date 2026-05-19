package com.sonik.service;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Song;

import java.util.List;

/**
 * Handles the user's favourite songs (library)
 */
public interface LibraryService {

    /**
     * Adds a song to the current user's favourites.
     * If the song is not yet persisted, it is saved first. If it is already a favourite, nothing happens.
     * @param song The Song to add
     * @throws DuplicateIdException       If a library entry with the same ID already exists
     * @throws DataAccessException        If a database error occurs
     * @throws IncorrectArgumentException If any song field is invalid
     * @throws ObjectNotFoundException    If the song cannot be found after being persisted
     */
    void addFavouriteSong(Song song) throws DuplicateIdException, DataAccessException, IncorrectArgumentException, ObjectNotFoundException;

    /**
     * Removes a song from the current user's favourites.
     * If the song is not persisted or not a favourite, nothing happens.
     * @param song The Song to remove
     */
    void removeFavouriteSong(Song song);

    /**
     * Returns all favourite songs of the current user.
     * @return A list of favourite Songs, or an empty list if none exist or an error occurs
     */
    List<Song> getFavouriteSongs();

    /**
     * Checks whether a song is in the current user's favourites.
     * @param song The Song to check
     * @return true if the song is a favourite, false otherwise
     * @throws DataAccessException     If a database error occurs
     * @throws ObjectNotFoundException If the song cannot be found by its URL
     */
    boolean isFavourite(Song song) throws DataAccessException, ObjectNotFoundException;
}
