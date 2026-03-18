package com.sonik.services;

import com.sonik.old.login_old.dao.UserDAO;
import com.sonik.old.login_old.exceptions.DataAccessException;
import com.sonik.old.login_old.exceptions.DuplicateIdException;
import com.sonik.old.login_old.exceptions.ObjectNotFoundException;
import com.sonik.old.login_old.model.User;

public class UserService {

    private final UserDAO userDAO;
    private final PasswordService passwordService;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.passwordService = new PasswordService();
    }

    /**
     * Creates the hash password and account if the user doesn't exists in the DDBB
     *
     * @param user
     */
    public void register(User user) throws DuplicateIdException, DataAccessException {
        if (!userDAO.existsUser(user)) {
            String hashed = passwordService.hashPassword(user.getPassword_hash());
            user.setPassword_hash(hashed);
            userDAO.createUser(user);
        }
    }

    /**
     * Returns true if the user exists in the DDBB
     *
     * @param userName
     * @param password
     * @return
     */
    public boolean login(String userName, String password) throws ObjectNotFoundException {
        User user = userDAO.findUser(userName);
        if (user == null) return false;

        return passwordService.checkPassword(password, user.getPassword_hash());
    }
}