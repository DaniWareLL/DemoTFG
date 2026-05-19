package com.sonik.service;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.User;

/**
 * Handles the logic behind the login views(registering users, logging them in, logging out)
 */
public interface AuthService {

    /**
     * Registers a new user. Hashes the password and assigns default preferences before persisting.
     * @param user The User to register
     * @throws DuplicateIdException       If a user with the same username already exists
     * @throws DataAccessException        If a database error occurs
     * @throws IncorrectArgumentException If the password is blank
     */
    void register(User user) throws DuplicateIdException, DataAccessException, IncorrectArgumentException;


    /**
     * Authenticates a user by username and password. Starts a session if successful.
     * @param username The username to look up
     * @param password The plain text password to verify
     * @return true if credentials are valid, false otherwise
     * @throws ObjectNotFoundException    If no user with the given username exists
     * @throws DataAccessException        If a database error occurs
     * @throws IncorrectArgumentException If the password is blank
     */
    boolean login(String username, String password) throws ObjectNotFoundException, DataAccessException, IncorrectArgumentException;


    /**
     * Logs out the current user and clears the session.
     */
    void logout();
}
