package com.sonik.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainModel {
    static void main() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sonik");
        EntityManager em = emf.createEntityManager();
    }
}
