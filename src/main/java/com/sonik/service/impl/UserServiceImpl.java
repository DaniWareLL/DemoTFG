package com.sonik.service.impl;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.exceptions.UserValidationException;
import com.sonik.domain.model.User;
import com.sonik.domain.model.UserPref;
import com.sonik.domain.repository.UserRepository;
import com.sonik.service.AuthService;
import com.sonik.service.PasswordService;
import com.sonik.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final PasswordService  passwordService;

    public UserServiceImpl(UserRepository userRepository, AuthService authService,  PasswordService passwordService) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.passwordService = passwordService;
    }

    @Override
    public UserPref getPreferences(String username) throws ObjectNotFoundException, DataAccessException {
            User user = userRepository.findByUsername(username);
            return user.getPreferences();
    }

    @Override
    public void updatePreferences(String username, UserPref newPreferences) throws DataAccessException, ObjectNotFoundException {

            User user = userRepository.findByUsername(username);

            user.getPreferences().setInterfaceTheme(newPreferences.getInterfaceTheme());
            user.getPreferences().setStreamingQuality(newPreferences.getStreamingQuality());
            user.getPreferences().setAudioSource(newPreferences.getAudioSource());

            userRepository.update(user);

    }

    @Override
    public void changeUsername(String oldUsername, String newUsername)
            throws DataAccessException, ObjectNotFoundException, UserValidationException {

        User user = userRepository.findByUsername(oldUsername);

        // Validar que el nuevo username no exista
        if (userRepository.existsByUsername(newUsername)) {
            throw new UserValidationException(UserValidationException.DUPLICATE_USERNAME);
        }

        user.setUserName(newUsername);
        userRepository.update(user);
    }


    @Override
    public void changePassword(String username, String currentPassword, String newPassword)
            throws DataAccessException, ObjectNotFoundException, UserValidationException {

        User user = userRepository.findByUsername(username);

        // Validar contraseña actual
        if (!authService.login(username, currentPassword)) {
            throw new UserValidationException(UserValidationException.INVALID_CURRENT_PASSWORD);
        }

        // Cifrar nueva contraseña usando AuthService
        user.setPassword_hash(passwordService.hashPassword(newPassword));

        userRepository.update(user);
    }


}
