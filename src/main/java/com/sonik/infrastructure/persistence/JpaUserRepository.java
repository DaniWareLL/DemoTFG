package com.sonik.infrastructure.persistence;

import com.sonik.domain.model.User;
import com.sonik.domain.repository.UserRepository;

import java.util.List;

/**
 * Implementation of {@link com.sonik.domain.repository.UserRepository UserRepository}
 */
public class JpaUserRepository implements UserRepository {
    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
