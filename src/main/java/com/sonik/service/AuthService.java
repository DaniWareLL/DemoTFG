package com.sonik.service;

import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.User;

/**
 * Handles the logic behind the login view(registering users, logging them in, logging out)
 */
public interface AuthService {
    /**
     * Registra un nuevo usuario cifrando su contraseña.
     */
    void register(User user) throws DuplicateIdException;

    /**
     * Valida las credenciales y devuelve el usuario si es correcto.
     */
    Boolean login(String username, String password) throws ObjectNotFoundException;

    /**
     * Finaliza la sesión actual (si manejas estado de sesión).
     */
    void logout();
}
