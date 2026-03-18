package com.sonik.repository.interfaces;

import com.sonik.old.login_old.exceptions.DataAccessException;
import com.sonik.old.login_old.exceptions.DuplicateIdException;
import com.sonik.old.login_old.exceptions.ObjectNotFoundException;
import com.sonik.old.login_old.model.User;

public interface UserDAO {

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
     * @return
     */
    public User findUser (String email) throws ObjectNotFoundException;
}
