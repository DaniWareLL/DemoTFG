package com.sonik.domain.repository;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;

import java.util.List;

/**
 * Operates over the JPA entity Playlist
 */
public interface PlaylistRepository {

    Playlist findById(Long id) throws DataAccessException, ObjectNotFoundException;

    void save(Playlist playlist) throws DuplicateIdException, DataAccessException;

    void delete(Playlist playlist) throws DataAccessException, ObjectNotFoundException;
}
