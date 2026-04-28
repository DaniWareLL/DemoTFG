package com.sonik.service.impl;

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

    // TODO: The attribute "position" doesn't actually mean position, its more like priority(no duplicates check)
    /**
     * Adds a song to the playlist
     * @param playlist The playlist where the song will be added
     * @param song The song to add
     * @param position The position inside the playlist
     * @throws IncorrectArgumentException
     * @throws DuplicateIdException
     */
    @Override
    public void addSongToPlaylist(Playlist playlist, Song song, int position) throws IncorrectArgumentException, DuplicateIdException {
        for (PlaylistsSongs playlistsSongs : playlist.getSongs()) {
            if (playlistsSongs.getSong().getId() == song.getId()) {
                throw new DuplicateIdException("Song with id " + song.getId()
                        + " already exists in playlist " + playlist.getName());
            }
        }
        PlaylistsSongs playlistsSongs = new PlaylistsSongs(playlist, song, position, LocalDate.now());
        playlist.getSongs().add(playlistsSongs);
    }

    @Override
    public void deleteSongFromPlaylist(Playlist playlist, Song song) throws ObjectNotFoundException {
        Iterator<PlaylistsSongs> iterator = playlist.getSongs().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getSong().equals(song)) {
                iterator.remove();
                return;
            }
        }
        throw new ObjectNotFoundException("Song doesn't exist in the playlist.");
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
