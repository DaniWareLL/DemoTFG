package com.sonik.service;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.User;

/**
 * Handles the logic behind the login views(registering users, logging them in, logging out)
 */
public interface AuthService {
    /**
     * Registra un nuevo usuario cifrando su contraseña.
     */
    void register(User user) throws DuplicateIdException, DataAccessException;

    /**
     * Valida las credenciales y devuelve true si es correcto.
     */
    Boolean login(String username, String password) throws ObjectNotFoundException, DataAccessException;

    /**
     * Finaliza la sesión actual (si manejas estado de sesión).
     */
    void logout();
}
