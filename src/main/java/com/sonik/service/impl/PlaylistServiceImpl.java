package com.sonik.service.impl;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.PlaylistsSongs;
import com.sonik.domain.model.Song;
import com.sonik.domain.repository.PlaylistRepository;
import com.sonik.service.PlaylistService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of the {@link PlaylistService Playlist Service}
 */
public class PlaylistServiceImpl implements PlaylistService {

    private PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    /**
     * Creates a new Playlist
     * @param playlist The playlist to be created
     * @throws DuplicateIdException If a playlist with the same id already exists
     * @throws DataAccessException If there was an error accessing the data
     */
    @Override
    public void createPlaylist(Playlist playlist) throws DuplicateIdException, DataAccessException {
        playlistRepository.save(playlist);
    }

    public List<Playlist> findAllPlaylistsForUser(String username) throws ObjectNotFoundException, DataAccessException, IncorrectArgumentException {
        return playlistRepository.findAllByUsername(username);
    }

    // TODO: The attribute "position" doesn't actually mean position, it's more like priority(no duplicates check)
    /**
     * Adds a song to the playlist
     * @param playlist The playlist where the song will be added
     * @param song The song to add
     * @throws IncorrectArgumentException
     * @throws DuplicateIdException
     */
    @Override
    public void addSongToPlaylist(Playlist playlist, Song song) throws IncorrectArgumentException, DuplicateIdException, DataAccessException {
        playlistRepository.addSongToPlaylist(playlist, song);
    }

    @Override
    public void deleteSongFromPlaylist(Playlist playlist, Song song) throws ObjectNotFoundException, DataAccessException {
        playlistRepository.removeSongFromPlaylist(playlist, song);
    }

    @Override
    public void deletePlaylist(Playlist playlist) throws ObjectNotFoundException, DataAccessException {
        playlistRepository.delete(playlist);
    }

    @Override
    public List<Song> getSongs(Playlist playlist) {
        List<Song> songs = new ArrayList<>();
        for (PlaylistsSongs playlistsSongs : playlist.getSongs() ) {
            songs.add(playlistsSongs.getSong());
        }
        return songs;
    }
}
