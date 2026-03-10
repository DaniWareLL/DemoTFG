package com.martinLillo.login.service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordService {

    /**
     * Converts the password into a secure hash
     *
     * @param plainPassword
     * @return
     */
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Compares the plain password to the hash to validate the login
     *
     * @param plainPassword
     * @param hashedPassword
     * @return
     */
    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}