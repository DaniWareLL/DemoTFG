package com.martinLillo.login.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    static void main() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("login");
        EntityManager em = emf.createEntityManager();
    }
}
