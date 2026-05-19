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
     * Creates a new playlist.
     * @param playlist The Playlist to create
     * @throws DuplicateIdException If a playlist with the same ID already exists
     * @throws DataAccessException  If a database error occurs
     */
    void createPlaylist(Playlist playlist) throws DuplicateIdException, DataAccessException;

    /**
     * Returns all playlists belonging to the given user.
     * @param username The username to search by
     * @return A list of Playlists owned by the user
     * @throws ObjectNotFoundException    If no user with the given username exists
     * @throws DataAccessException        If a database error occurs
     * @throws IncorrectArgumentException If the username is blank
     */
    List<Playlist> findAllPlaylistsForUser(String username) throws ObjectNotFoundException, DataAccessException, IncorrectArgumentException;

    /**
     * Adds a song to a playlist. If the song is not yet persisted, it is saved first.
     * @param playlist The Playlist to add the song to
     * @param song     The Song to add
     * @throws IncorrectArgumentException If any song field is invalid
     * @throws DuplicateIdException       If the song is already in the playlist
     * @throws DataAccessException        If a database error occurs
     * @throws ObjectNotFoundException    If the playlist or song cannot be found
     */
    void addSongToPlaylist(Playlist playlist, Song song) throws IncorrectArgumentException, DuplicateIdException, DataAccessException, ObjectNotFoundException;

    /**
     * Removes a song from a playlist.
     * @param playlist The Playlist to remove the song from
     * @param song     The Song to remove
     * @throws ObjectNotFoundException If the playlist or song cannot be found
     * @throws DataAccessException     If a database error occurs
     */
    void deleteSongFromPlaylist(Playlist playlist, Song song) throws ObjectNotFoundException, DataAccessException;

    /**
     * Deletes a playlist.
     * @param playlist The Playlist to delete
     * @throws ObjectNotFoundException If the playlist cannot be found
     * @throws DataAccessException     If a database error occurs
     */
    void deletePlaylist(Playlist playlist) throws ObjectNotFoundException, DataAccessException;

    /**
     * Returns all songs in a playlist.
     * @param playlist The Playlist to extract songs from
     * @return A list of Songs in the playlist
     */
    List<Song> getSongs(Playlist playlist);

}
