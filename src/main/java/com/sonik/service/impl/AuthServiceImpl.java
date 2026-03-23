package com.sonik.service.impl;

import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.User;
import com.sonik.domain.repository.UserRepository;
import com.sonik.service.AuthService;

/**
 * Implementation of the {@link AuthService Authentication Service}
 */
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordServiceImpl passwordService;

    // Inyección de dependencias por constructor
    public AuthServiceImpl(UserRepository userRepository, PasswordServiceImpl passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public void register(User user) throws DuplicateIdException {
        // 1. Cifrar la contraseña usando el servicio
        String hashed = passwordService.hashPassword(user.getPassword_hash());
        user.setPassword_hash(hashed);

        // 2. Guardar en repositorio
        userRepository.save(user);
    }

    @Override
    public Boolean login(String username, String password) throws ObjectNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) return false;

        return passwordService.checkPassword(password, user.getPassword_hash());
    }

    @Override
    public void logout() {
        // Limpiar sesión
    }
}
