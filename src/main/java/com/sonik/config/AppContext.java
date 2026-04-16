package com.sonik.config;

import com.sonik.domain.repository.PlaylistRepository;
import com.sonik.domain.repository.SongRepository;
import com.sonik.domain.repository.UserRepository;
import com.sonik.infrastructure.persistence.JpaPlaylistRepository;
import com.sonik.infrastructure.persistence.JpaSongRepository;
import com.sonik.infrastructure.persistence.JpaUserRepository;
import com.sonik.service.AuthService;
import com.sonik.service.impl.AuthServiceImpl;
import com.sonik.service.impl.PasswordServiceImpl;
import jakarta.persistence.EntityManagerFactory;

/**
 * This class essentially initializes the application, necessary views and controllers, persistence and JPA.
 * It uses {@link AppConfig AppConfig} to load the default configuration for the application
 */
public class AppContext {

    private static EntityManagerFactory emf;
    private static UserRepository jpaUserRepository;
    private static SongRepository jpaSongRepository;
    private static PlaylistRepository jpaPlaylistRepository;
    private static AuthService authService;


    /**
     * <strong>IMPORTANT!!</strong> This constructor is not supposed to be used at all (hence the private access modifier),
     * instead its static method {@link #initializeApplication() init()} should be called
     *
     * @see #initializeApplication()
     */
    private AppContext() {
    }

    /**
     * Initializes the application's dependencies and its main components every time it's started(dependency injector)
     */
    public static void initializeApplication() {

        emf = PersistenceConfig.initializePersistence();
        jpaUserRepository = new JpaUserRepository(emf);
        jpaSongRepository = new JpaSongRepository(emf);
        jpaPlaylistRepository = new JpaPlaylistRepository(emf);
        authService = new AuthServiceImpl(jpaUserRepository, new PasswordServiceImpl());


        // Create AudioChain(Chain of responsibility)


    }

    /**
     * Closes every resource and shuts down the application
     * (this method should only be invoked when exiting the application)
     */
    public static void shutDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public static UserRepository getJpaUserRepository() {
        return jpaUserRepository;
    }

    public static SongRepository getJpaSongRepository() {
        return jpaSongRepository;
    }

    public static PlaylistRepository getJpaPlaylistRepositor() {
        return jpaPlaylistRepository;
    }

    public static AuthService getAuthService() {
        return authService;
    }
}
