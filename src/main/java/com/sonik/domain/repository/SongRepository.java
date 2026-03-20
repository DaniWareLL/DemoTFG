package com.sonik.domain.repository;

import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.Song;

import java.util.List;

/**
 * Operations over the JPA entity Song
 */
public interface SongRepository {

    List<Song> findAll();

    Song findById(Long id);

    void save(Song song);

    void delete(Song song);

}
