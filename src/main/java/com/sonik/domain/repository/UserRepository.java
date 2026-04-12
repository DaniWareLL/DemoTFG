package com.sonik.domain.repository;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.User;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Interface to execute operations on the JPA entity User
 */
public interface UserRepository {

    /**
     * Retrieves a user entity matching the given id
     * @param id
     * @return
     * @throws DataAccessException if a data access error occurs
     * @throws ObjectNotFoundException if no user with the given ID exists.
     */
    User findById(Long id) throws DataAccessException, ObjectNotFoundException;

    /**
     * Retrieves a user entity matching the given username
     * @param username
     * @return the matching user entity
     * @throws DataAccessException if a data access error occurs
     * @throws ObjectNotFoundException if no user with the given username exists
     */
    User findByUsername(String username) throws DataAccessException, ObjectNotFoundException;

    /**
     * Creates a new user record in the data source
     * @param user
     * @throws DataAccessException if a data access error occurs
     * @throws DuplicateIdException if the user already exists
     */
    void create(User user) throws DataAccessException, DuplicateIdException;


    /**
     * Deletes a user entity matching the given user object
     * @param user
     * @throws DataAccessException if a data access error occurs
     * @throws ObjectNotFoundException if the user does not exist
     */
    void delete(User user) throws DataAccessException, ObjectNotFoundException;


    /**
     * Checks if a user with the specified username exists in the data source
     * @param username
     * @return true if a user with the given username exists; false otherwise
     * @throws DataAccessException if a data access error occurs
     */
    boolean existsByUsername(String username) throws DataAccessException;
}
