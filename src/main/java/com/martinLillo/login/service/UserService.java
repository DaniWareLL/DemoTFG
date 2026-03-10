package com.martinLillo.login.service;

import com.martinLillo.login.dao.LoginDAO;
import com.martinLillo.login.exceptions.DataAccessException;
import com.martinLillo.login.exceptions.DuplicateIdException;
import com.martinLillo.login.model.User;

public class UserService {

    private final LoginDAO loginDAO;
    private final PasswordService passwordService;

    public UserService(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
        this.passwordService = new PasswordService();
    }

    /**
     * Creates the hash password and account if the user doesn't exists in the DDBB
     *
     * @param user
     */
    public void register(User user) throws DuplicateIdException, DataAccessException {
        if (!loginDAO.existsUser(user)) {
            String hashed = passwordService.hashPassword(user.getPassword_hash());
            user.setPassword_hash(hashed);
            loginDAO.createUser(user);
        }
    }

    /**
     * Returns true if the user exists in the DDBB
     *
     * @param email
     * @param password
     * @return
     */
    public boolean login(String email, String password) {
        User user = loginDAO.findUser(email, password);
        if (user == null) return false;

        return passwordService.checkPassword(password, user.getPassword_hash());
    }
}