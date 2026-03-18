package com.sonik.old.login_old.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    static void main() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("login_old");
        EntityManager em = emf.createEntityManager();
    }
}
