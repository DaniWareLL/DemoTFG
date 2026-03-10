package com.martinLillo.login.service;

import com.martinLillo.login.dao.LoginDAO;
import com.martinLillo.login.model.User;

public class UserService {

    private final LoginDAO loginDAO;
    private final PasswordService passwordService;

    public UserService(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
        this.passwordService = new PasswordService();
    }

    public void register(User user) {
        if (!loginDAO.existsUser(user)) {
            String hashed = passwordService.hashPassword(user.getPassword_hash());
            user.setPassword_hash(hashed);
            loginDAO.createUser(user);
        }
    }

    public boolean login(String email, String password) {
        User user = loginDAO.findUser(email, password);
        if (user == null) return false;

        return passwordService.checkPassword(password, user.getPassword_hash());
    }
}