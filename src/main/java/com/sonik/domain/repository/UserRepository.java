package com.sonik.domain.repository;

import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.User;

import java.util.List;

/**
 * Interface to execute operations on the JPA entity User
 */
public interface UserRepository {

    List<User> findAll();

    User findById(Long id);

    void save(User user);

    void delete(User user);

}
