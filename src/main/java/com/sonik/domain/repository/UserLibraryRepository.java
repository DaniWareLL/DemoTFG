package com.sonik.domain.repository;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.model.UserLibrary;
import com.sonik.domain.model.UserLibraryId;

import java.util.List;

public interface UserLibraryRepository {

    /**
     * Persists a new UserLibrary entry.
     * If the transaction fails, changes are rolled back.
     * @param entry The UserLibrary to persist
     * @throws DuplicateIdException If an entry with the same ID already exists
     * @throws DataAccessException  If a database connection error occurs
     */
    void create(UserLibrary entry) throws DataAccessException, DuplicateIdException;

    /**
     * Deletes a UserLibrary entry by its composite ID.
     * If the entry does not exist, nothing happens.
     * If the transaction fails, changes are rolled back.
     * @param id The UserLibraryId to delete
     * @throws DataAccessException If a database error occurs and changes are rolled back
     */
    void delete(UserLibraryId id) throws DataAccessException;

    /**
     * Finds all library entries belonging to a user.
     * @param userId The ID of the User
     * @return A list of UserLibrary entries, or an empty list if none exist
     * @throws DataAccessException If a database connection error occurs
     */
    List<UserLibrary> findByUserId(int userId) throws DataAccessException;

    /**
     * Checks whether a song is already in a user's library.
     * @param userId The ID of the User
     * @param songId The ID of the Song
     * @return true if the entry exists, false otherwise
     * @throws DataAccessException If a database connection error occurs
     */
    boolean exists(int userId, int songId) throws DataAccessException;
}
