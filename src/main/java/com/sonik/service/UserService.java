package com.sonik.service;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.exceptions.UserValidationException;
import com.sonik.domain.model.UserPref;

/**
 * Provides operations related to user data management.
 */
public interface UserService {

    /**
     * Retrieves the preferences of the user with the given username.
     *
     * @param username the username of the user
     * @return the user's preferences
     * @throws ObjectNotFoundException
     * @throws DataAccessException
     */
    UserPref getPreferences(String username) throws ObjectNotFoundException, DataAccessException;

    /**
     * Updates the preferences of the user with the given username.
     *
     * @param username the username of the user
     * @param newPreferences the new preferences to apply
     * @throws ObjectNotFoundException
     * @throws DataAccessException
     */
    void updatePreferences(String username, UserPref newPreferences) throws ObjectNotFoundException, DataAccessException;

    /**
     * Changes the username of an existing user.
     * This operation updates the user's profile information but does not
     * affect authentication logic or credentials validation.
     *
     * @param oldUsername the current username of the user
     * @param newUsername the new username to assign
     * @throws ObjectNotFoundException
     * @throws DataAccessException
     * @throws UserValidationException the current username is taken
     */
    void changeUsername(String oldUsername, String newUsername) throws ObjectNotFoundException, DataAccessException, UserValidationException;


    /**
     * Updates the password of the user with the given username.
     * The service is responsible for applying any required transformations,
     * such as hashing the new password before persisting it.
     *
     * @param username the current username
     * @param currentPassword the current password of the user
     * @param newPassword the new raw password to set
     * @throws ObjectNotFoundException
     * @throws DataAccessException
     * @throws UserValidationException the credentials are incorrect
     */
    void changePassword(String username, String currentPassword, String newPassword) throws ObjectNotFoundException, DataAccessException, UserValidationException;
}
