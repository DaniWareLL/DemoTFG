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

    Playlist findById(Long id) throws DataAccessException, ObjectNotFoundException;

    void save(Playlist playlist) throws DuplicateIdException, DataAccessException;

    void delete(Playlist playlist) throws DataAccessException, ObjectNotFoundException;

    /**
     * Finds all playlists associated with the user received
     * @return A list containing all playlists associated with said user
     * @param username The name of the user who is associated with the playlists
     * @throws DataAccessException If there is any error accessing the data
     * @throws ObjectNotFoundException If there is no username found with said name
     */
    List<Playlist> findAllByUsername(String username) throws DataAccessException, ObjectNotFoundException, IncorrectArgumentException;

    void addSongToPlaylist(Playlist playlist, Song song) throws DuplicateIdException, IncorrectArgumentException, DataAccessException, ObjectNotFoundException;

    void removeSongFromPlaylist(Playlist playlist, Song song) throws DataAccessException;
}
