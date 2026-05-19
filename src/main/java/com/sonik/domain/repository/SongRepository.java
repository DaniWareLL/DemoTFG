package com.sonik.domain.repository;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Song;


/**
 * Operations over the JPA entity Song
 */
public interface SongRepository {

    /**
     * Finds a song by its original URL.
     * @param url The URL to search for
     * @return The matching Song, or null if none exists
     * @throws DataAccessException If a database connection error occurs
     */
    Song findByUrl(String url) throws DataAccessException;

    /**
     * Checks whether a song with the given URL already exists in the database.
     * @param url The URL to check
     * @return true if a song with the given URL exists, false otherwise
     * @throws DataAccessException If a database connection error occurs
     */
    boolean existsUrl(String url) throws ObjectNotFoundException, DataAccessException;

    /**
     * Finds a {@link com.sonik.domain.model.Song Song} by id and returns it
     * @param id The id from the song to search for
     * @return The song with the corresponding id
     * @throws DataAccessException If JPA finds any errors when searching for the song
     * @throws ObjectNotFoundException If no song is found with such id
     */
    Song findById(int id) throws DataAccessException, ObjectNotFoundException;

    /**
     * Saves a {@link com.sonik.domain.model.Song Song} to the database
     * @param song The song to save
     * @throws DuplicateIdException If a song with the same id already exists in the database
     * @throws DataAccessException If the database could not be accessed
     */
    void save(Song song) throws DuplicateIdException, DataAccessException;

    /**
     * Deletes a {@link com.sonik.domain.model.Song Song} from the database
     * @param song The song to be deleted
     * @throws DataAccessException If the song couldn't be deleted
     * @throws ObjectNotFoundException If the song was not found
     */
    void delete(Song song) throws DataAccessException, ObjectNotFoundException;

}
