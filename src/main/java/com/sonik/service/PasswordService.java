package com.sonik.service;

public interface PasswordService {
    /**
     * Converts the password into a secure hash
     *
     * @param plainPassword
     * @return
     */
    public String hashPassword(String plainPassword);

    /**
     * Compares the plain password to the hash to validate the login_old
     *
     * @param plainPassword
     * @param hashedPassword
     * @return
     */
    public boolean checkPassword(String plainPassword, String hashedPassword);
}
