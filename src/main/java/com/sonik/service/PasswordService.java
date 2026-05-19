package com.sonik.service;

public interface PasswordService {

    /**
     * Hashes a plain text password using BCrypt.
     * @param plainPassword The plain text password to hash
     * @return The BCrypt-hashed password
     */
    String hashPassword(String plainPassword);

    /**
     * Checks whether a plain text password matches a BCrypt-hashed password.
     * @param plainPassword  The plain text password to verify
     * @param hashedPassword The BCrypt hash to compare against
     * @return true if the password matches, false otherwise
     */
    boolean checkPassword(String plainPassword, String hashedPassword);
}
