package com.martinLillo.login.dao;

import com.martinLillo.login.exceptions.DataAccessException;
import com.martinLillo.login.exceptions.DuplicateIdException;
import com.martinLillo.login.exceptions.ObjectNotFoundException;
import com.martinLillo.login.model.User;

public interface LoginDAO {

    /**
     * Creates and validates a new user in the program
     *
     * @param user
     * @throws DataAccessException
     * @throws DuplicateIdException
     */
    public void createUser (User user) throws DataAccessException, DuplicateIdException;

    /**
     * Deletes a user from the program
     *
     * @param user
     * @throws DataAccessException
     */
    public void deleteUser (User user) throws DataAccessException;

    /**
     * Returns true if userName exists in the BBDD
     *
     * @param user
     * @return true if exists
     */
    public boolean existsUser (User user);


    /**
     * Updates user
     *
     * @param user
     * @throws DataAccessException
     * @throws ObjectNotFoundException
     */
    public void updateUser (User user) throws DataAccessException, ObjectNotFoundException;

    /**
     * Find user
     *
     * @param email
     * @param password
     * @return
     */
    public User findUser (String email, String password);
}
