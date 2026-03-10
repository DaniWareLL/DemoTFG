package com.martinLillo.login.dao;

import com.martinLillo.login.db.ConnectionDB;
import com.martinLillo.login.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginDAO_Impl implements LoginDAO {

    /**
     * Only one instance for every method
     */
    private final EntityManagerFactory emf;

    public LoginDAO_Impl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void createUser(User user) {
        if(!existsUser(user)) {

        }
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public boolean existsUser(User user) {
        EntityManager em = emf.createEntityManager();
        return false;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User findUser(String email, String password) {
        return null;
    }
}
