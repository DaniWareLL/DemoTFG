package com.sonik.service;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.exceptions.UserValidationException;
import com.sonik.domain.model.UserPref;

/**
 * Provides operations related to user data management.
 */
public interface UserService {

    /**
     * Returns the current user's preferences from the active session.
     * @return The current UserPref
     */
    UserPref getPreferences();

    /**
     * Updates the current user's preferences and syncs the session.
     * @param newPreferences The UserPref containing the updated values
     * @throws DataAccessException        If a database error occurs
     * @throws ObjectNotFoundException    If the user cannot be found
     * @throws IncorrectArgumentException If any preference field is invalid
     */
    void updatePreferences(UserPref newPreferences) throws DataAccessException, ObjectNotFoundException, IncorrectArgumentException;

    /**
     * Changes the current user's username.
     * @param oldUsername The current username
     * @param newUsername The new username to assign
     * @throws UserValidationException    If the new username is already taken
     * @throws DataAccessException        If a database error occurs
     * @throws ObjectNotFoundException    If the user cannot be found
     * @throws IncorrectArgumentException If the new username is blank
     */
    void changeUsername(String oldUsername, String newUsername) throws DataAccessException, ObjectNotFoundException, UserValidationException, IncorrectArgumentException;

    /**
     * Changes the current user's password after validating the current one.
     * @param username        The username of the user
     * @param currentPassword The current plain text password to verify
     * @param newPassword     The new plain text password to set
     * @throws UserValidationException    If the current password is incorrect
     * @throws DataAccessException        If a database error occurs
     * @throws ObjectNotFoundException    If the user cannot be found
     * @throws IncorrectArgumentException If any password field is blank
     */
    void changePassword(String username, String currentPassword, String newPassword) throws DataAccessException, ObjectNotFoundException, UserValidationException, IncorrectArgumentException;
}
