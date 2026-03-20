package com.sonik.infrastructure.persistence;

import com.sonik.domain.model.Playlist;
import com.sonik.domain.repository.PlaylistRepository;

import java.util.List;

/**
 * Implementation of {@link com.sonik.domain.repository.PlaylistRepository PlaylistRepository}
 */
public class JpaPlaylistRepository implements PlaylistRepository {
    @Override
    public List<Playlist> findAll() {
        return List.of();
    }

    @Override
    public Playlist findById(Long id) {
        return null;
    }

    @Override
    public void save(Playlist playlist) {

    }

    @Override
    public void delete(Playlist playlist) {

    }
}
