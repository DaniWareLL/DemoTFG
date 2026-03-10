package com.martinLillo.login.dao;

import com.martinLillo.login.model.User;

public interface LoginDAO {

    /**
     * Creates and validates a new user in the program
     *
     * @param user
     */
    public void createUser (User user);

    /**
     * Deletes a user from the program
     *
     * @param user
     */
    public void deleteUser (User user);

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
     */
    public void updateUser (User user);

    /**
     * Find user
     *
     * @param email
     * @param password
     * @return
     */
    public User findUser (String email, String password);
}
