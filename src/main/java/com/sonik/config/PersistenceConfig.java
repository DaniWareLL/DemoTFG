package com.sonik.config;

import jakarta.persistence.EntityManager;

/**
 * This class initializes the Hibernate and JPA components
 */
public class PersistenceConfig {

    /**
     * This constructor is not meant to be used at all, instead use {@link #initializePersistence()}
     */
    private PersistenceConfig() {}

    /**
     * Initializes and configures the access to the DB and entities
     */
    protected static EntityManager initializePersistence() {
        /*
        Create EntityManager through EntityManagerFactory and return it
        Configure DB access
        Configure Hibernate with its persistence unit
        */
        return null;
    }

}
