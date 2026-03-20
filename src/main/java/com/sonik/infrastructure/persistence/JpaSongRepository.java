package com.sonik.infrastructure.persistence;

import com.sonik.domain.model.Song;
import com.sonik.domain.repository.SongRepository;

import java.util.List;

/**
 * Implementation of {@link com.sonik.domain.repository.SongRepository SongRepository}
 */
public class JpaSongRepository implements SongRepository {
    @Override
    public List<Song> findAll() {
        return List.of();
    }

    @Override
    public Song findById(Long id) {
        return null;
    }

    @Override
    public void save(Song song) {

    }

    @Override
    public void delete(Song song) {

    }
}
