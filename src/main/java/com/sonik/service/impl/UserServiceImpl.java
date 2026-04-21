package com.sonik.service.impl;

import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
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
    public UserPref getPreferences() {
        return UserSession.getPreferences();
    }

    @Override
    public void updatePreferences(UserPref newPreferences) throws DataAccessException, ObjectNotFoundException, IncorrectArgumentException {

        User user = UserSession.getUser();
        UserPref pref = user.getPreferences();

        pref.setInterfaceTheme(newPreferences.getInterfaceTheme());
        pref.setStreamingQuality(newPreferences.getStreamingQuality());
        pref.setAudioSource(newPreferences.getAudioSource());

        userRepository.update(user);

        // Actualizar la sesión en memoria
        UserSession.updatePreferences(pref);

    }

    @Override
    public void changeUsername(String oldUsername, String newUsername)
            throws DataAccessException, ObjectNotFoundException, UserValidationException, IncorrectArgumentException {

        User user = UserSession.getUser();

        if (userRepository.existsByUsername(newUsername)) {
            throw new UserValidationException(UserValidationException.DUPLICATE_USERNAME);
        }

        user.setUserName(newUsername);
        userRepository.update(user);

        // Actualizar sesión
        UserSession.start(user);
    }


    @Override
    public void changePassword(String username, String currentPassword, String newPassword)
            throws DataAccessException, ObjectNotFoundException, UserValidationException, IncorrectArgumentException {

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
