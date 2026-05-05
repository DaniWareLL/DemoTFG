package com.sonik.service.impl;

import com.sonik.config.AppContext;
import com.sonik.config.SessionStorage;
import com.sonik.config.UserSession;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.User;
import com.sonik.domain.model.UserPref;
import com.sonik.domain.repository.UserRepository;
import com.sonik.infrastructure.persistence.JpaUserRepository;
import com.sonik.service.AuthService;
import com.sonik.service.PasswordService;
import com.sonik.ui.navigation.ViewManager;
import com.sonik.ui.navigation.ViewType;


/**
 * Implementation of the {@link AuthService Authentication Service}
 */
public class AuthServiceImpl implements AuthService {

    /**
     * We use interfaces to comply with the Dependency Inversion Principle (DIP),
     * keeping the service independent of concrete implementations.
     */
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public AuthServiceImpl(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public void register(User user) throws DuplicateIdException, DataAccessException, IncorrectArgumentException {
        // Cifrar la contraseña usando el servicio
        String hashed = passwordService.hashPassword(user.getPassword_hash());
        user.setPassword_hash(hashed);

        // Crear preferencias por defecto del usuario
        UserPref userPref = new UserPref();
        userPref.setUser(user);
        user.setPreferences(userPref);

        // Guardar en repositorio
        userRepository.create(user);
    }

    @Override
    public boolean login(String username, String password) throws ObjectNotFoundException, DataAccessException, IncorrectArgumentException {
        User user = userRepository.findByUsername(username);
        if (password.trim().isBlank()) throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.EMPTY_PASSWORD);

        if (passwordService.checkPassword(password, user.getPassword_hash())) {
            UserSession.start(user); // Guardar datos del usuario en memoria
            return true;
        }

        return false;
    }

    @Override
    public void logout() {
        SessionStorage.clear();
    }
}
