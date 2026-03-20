package com.sonik.domain.repository;

import com.sonik.domain.model.Playlist;

import java.util.List;

/**
 * Operates over the JPA entity Playlist
 */
public interface PlaylistRepository {

    List<Playlist> findAll();

    Playlist findById(Long id);

    void save(Playlist playlist);

    void delete(Playlist playlist);
}
