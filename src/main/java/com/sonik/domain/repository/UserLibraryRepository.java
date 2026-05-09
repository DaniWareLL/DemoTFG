package com.sonik.domain.repository;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.model.UserLibrary;
import com.sonik.domain.model.UserLibraryId;

import java.util.List;

public interface UserLibraryRepository {

    void create(UserLibrary entry) throws DataAccessException, DuplicateIdException;

    void delete(UserLibraryId id) throws DataAccessException;

    List<UserLibrary> findByUserId(int userId) throws DataAccessException;

    boolean exists(int userId, int songId) throws DataAccessException;
}
