package com.sonik.domain.repository;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.User;
import com.sonik.domain.model.UserPref;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Interface to execute operations on the JPA entity User
 */
public interface UserRepository {

    /**
     * Finds a user by their username.
     * @param username The username to search for
     * @return The matching User
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#EMPTY_USERNAME} If username is blank
     * @throws ObjectNotFoundException If no user with the given username exists
     * @throws DataAccessException <ul>
     *     <li>If more than one user with the same username is found</li>
     *     <li>If a database connection error occurs</li>
     * </ul>
     */
    User findByUsername(String username) throws DataAccessException, ObjectNotFoundException, IncorrectArgumentException;

    /**
     * Persists a new User. Checks for an existing user with the same username before persisting.
     * If the transaction fails, changes are rolled back.
     * @param user The User to persist
     * @throws DuplicateIdException If a user with the same username or ID already exists
     * @throws IncorrectArgumentException If the username is blank
     * @throws DataAccessException If a database error occurs and changes are rolled back
     */
    void create(User user) throws DataAccessException, DuplicateIdException, IncorrectArgumentException;


    /**
     * Deletes a user from the database.
     * If the transaction fails, changes are rolled back.
     * @param user The User to delete
     * @throws ObjectNotFoundException If the user does not exist in the database
     * @throws DataAccessException If a database error occurs and changes are rolled back
     */
    void delete(User user) throws DataAccessException, ObjectNotFoundException;



    /**
     * Checks whether a user with the given username exists in the database.
     * @param username The username to check
     * @return true if a user with the given username exists, false otherwise
     * @throws DataAccessException If a database connection error occurs
     */
    boolean existsByUsername(String username) throws DataAccessException;

    /**
     * Updates an existing user in the database.
     * If the transaction fails, changes are rolled back.
     * @param user The User with updated values to merge
     * @throws ObjectNotFoundException If the user does not exist in the database
     * @throws DataAccessException If a database error occurs and changes are rolled back
     */
    void update(User user) throws DataAccessException, ObjectNotFoundException;
}
