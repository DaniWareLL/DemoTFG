package com.sonik.domain.repository;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.Song;

import java.util.List;

/**
 * Operates over the JPA entity Playlist
 */
public interface PlaylistRepository {

    /**
     * Finds a {@link com.sonik.domain.model.Playlist Playlist} by id and returns it
     * @param id The id from the playlist to search for
     * @return The playlist with the corresponding id
     * @throws DataAccessException If there was an error accessing the data
     * @throws ObjectNotFoundException If no playlist is found with such id
     */
    Playlist findById(Long id) throws DataAccessException, ObjectNotFoundException;

    /**
     * Saves a {@link com.sonik.domain.model.Playlist Playlist} to the database
     * @param playlist The playlist to save
     * @throws DuplicateIdException If a playlist with the same id already exists in the database
     * @throws DataAccessException If there was an error accessing the data
     */
    void save(Playlist playlist) throws DuplicateIdException, DataAccessException;

    /**
     * Deletes a {@link com.sonik.domain.model.Playlist Playlist} from the database
     * @param playlist The playlist to be deleted
     * @throws DataAccessException When a playlist can't be deleted
     * @throws ObjectNotFoundException If the playlist could not be found
     */
    void delete(Playlist playlist) throws DataAccessException, ObjectNotFoundException;

    /**
     * Finds all playlists associated with the user received
     * @return A list containing all playlists associated with said user
     * @param username The name of the user who is associated with the playlists
     * @throws DataAccessException If there is any error accessing the data
     * @throws ObjectNotFoundException If there is no username found with said name
     */
    List<Playlist> findAllByUsername(String username) throws DataAccessException, ObjectNotFoundException, IncorrectArgumentException;

    /**
     * Adds a song to a playlist
     * @param playlist The playlist
     * @param song The song
     * @throws DuplicateIdException If the song already exists
     * @throws IncorrectArgumentException
     * @throws DataAccessException
     * @throws ObjectNotFoundException
     */
    void addSongToPlaylist(Playlist playlist, Song song) throws DuplicateIdException, IncorrectArgumentException, DataAccessException, ObjectNotFoundException;

    /**
     * Removes a song from a playlist.
     * If the transaction fails, changes are rolled back.
     * @param playlist The Playlist to remove the song from
     * @param song     The Song to remove
     * @throws DataAccessException If a database error occurs and changes are rolled back
     */
    void removeSongFromPlaylist(Playlist playlist, Song song) throws DataAccessException;
}
