package com.sonik.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * This class initializes the Hibernate and JPA components
 */
public class PersistenceConfig {

    private static final String persistenceUnitName = "sonik";
    /**
     * This constructor is not meant to be used at all, instead use {@link #initializePersistence()}
     */
    private PersistenceConfig() {}

    /**
     * Initializes and configures the access to the DB and entities
     */
    protected static EntityManagerFactory initializePersistence() {
        return Persistence.createEntityManagerFactory(persistenceUnitName);
    }

}
