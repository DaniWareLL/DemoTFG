package com.sonik.config;

import jakarta.persistence.EntityManager;

/**
 * This class essentially initializes the application, necessary views and controllers, persistence and JPA.
 * It uses {@link AppConfig AppConfig} to load the default configuration for the application
 */
public class AppContext {

    /**
     * <strong>IMPORTANT!!</strong> This constructor is not supposed to be used at all (hence the private access modifier),
     * instead its static method {@link #initializeApplication() init()} should be called
     *
     * @see #initializeApplication()
     */
    private AppContext() {}

    /**
     * Initializes the application's dependencies and its main components every time it's started(dependency injector)
     */
    public static void initializeApplication() {

        try (EntityManager em = PersistenceConfig.initializePersistence()) {
            /*
         Create services
         Create repositories
         Create AudioPlayer
         Create AudioChain(Chain of responsibility)
          */
        }


    }

    /**
     * Closes every resource and shuts down the application
     * (this method should only be invoked when exiting the application)
     */
    public static void shutDown() {
    }
}
