package com.sonik.service.impl;

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
    public void register(User user) throws DuplicateIdException, DataAccessException {
        // 1. Cifrar la contraseña usando el servicio
        String hashed = passwordService.hashPassword(user.getPassword_hash());
        try {
            user.setPassword_hash(hashed);
        } catch (IncorrectArgumentException e) {
            throw new DataAccessException(e.getMessage(), e);
        }

        // 2. Crear preferencias por defecto del usuario
        UserPref userPref = new UserPref(); // HIGH, DARK, YOUTUBE
        userPref.setUser(user);
        user.setPreferences(userPref);

        // 3. Guardar en repositorio
        userRepository.create(user);
    }

    @Override
    public Boolean login(String username, String password) throws ObjectNotFoundException, DataAccessException {
        User user = userRepository.findByUsername(username);
        if (user == null) return false;

        return passwordService.checkPassword(password, user.getPassword_hash());
    }

    @Override
    public void logout() {
        // Limpiar sesión
    }
}
