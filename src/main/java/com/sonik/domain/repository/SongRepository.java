package com.sonik.domain.repository;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Song;


/**
 * Operations over the JPA entity Song
 */
public interface SongRepository {

    Song findById(Long id) throws DataAccessException, ObjectNotFoundException;

    void save(Song song) throws DuplicateIdException, DataAccessException;

    void delete(Song song) throws DataAccessException, ObjectNotFoundException;

}
