package com.sonik.service;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.Song;

import java.util.List;

/**
 * Handles the user's playlists(add, remove, play songs... etc.)
 */
public interface PlaylistService {

    /**
     * Finds all playlists associated with a user
     * @param username The name of the user
     * @return
     * @throws ObjectNotFoundException
     * @throws DataAccessException
     */
    List<Playlist> findAllPlaylistsForUser(String username) throws ObjectNotFoundException, DataAccessException, IncorrectArgumentException;

    /**
     * Creates a new empty playlist
     * @param playlist
     */
    void createPlaylist(Playlist playlist) throws DuplicateIdException, DataAccessException;

    void addSongToPlaylist(Playlist playlist, Song song) throws IncorrectArgumentException, DuplicateIdException, DataAccessException;

    void deleteSongFromPlaylist(Playlist playlist, Song song) throws ObjectNotFoundException, DataAccessException;

    void deletePlaylist(Playlist playlist) throws ObjectNotFoundException, DataAccessException;

    List<Song> getSongs(Playlist playlist);

}
