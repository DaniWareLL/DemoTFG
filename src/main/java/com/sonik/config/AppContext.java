package com.sonik.config;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.repository.PlaylistRepository;
import com.sonik.domain.repository.SongRepository;
import com.sonik.domain.repository.UserRepository;
import com.sonik.infrastructure.audio.VlcjAudioPlayer;
import com.sonik.infrastructure.audio.YtDlpClient;
import com.sonik.infrastructure.persistence.JpaPlaylistRepository;
import com.sonik.infrastructure.persistence.JpaSongRepository;
import com.sonik.infrastructure.persistence.JpaUserRepository;
import com.sonik.service.*;
import com.sonik.service.audio.AudioExtractor;
import com.sonik.service.audio.AudioPlayer;
import com.sonik.service.impl.AuthServiceImpl;
import com.sonik.service.impl.PasswordServiceImpl;
import com.sonik.service.impl.SettingServiceImpl;
import com.sonik.service.impl.UserServiceImpl;
import com.sun.jna.NativeLibrary;
import jakarta.persistence.EntityManagerFactory;

import com.sonik.config.AppConfig;

/**
 * This class essentially initializes the application, necessary views and controllers, persistence and JPA.
 * It uses {@link AppConfig AppConfig} to load the default configuration for the application
 */
public class AppContext {

    private static EntityManagerFactory emf;

    private static AudioExtractor audioExtractor;
    private static AudioPlayer audioPlayer;

    private static UserRepository jpaUserRepository;
    private static SongRepository jpaSongRepository;
    private static PlaylistRepository jpaPlaylistRepository;

    private static AuthService authService;
    private static UserService userService;
    private static PasswordService  passwordService;
    private static PlayerService playerService;
    private static DownloadService downloadService;
    private static SettingService settingService;
    private static MetadataService metadataService;
    private static PlaylistService playlistService;


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
    public static void initializeApplication() throws DataAccessException {

        emf = PersistenceConfig.initializePersistence();

        audioExtractor = new YtDlpClient();
        audioPlayer = new VlcjAudioPlayer();

        jpaUserRepository = new JpaUserRepository(emf);
        jpaSongRepository = new JpaSongRepository(emf);
        jpaPlaylistRepository = new JpaPlaylistRepository(emf);

        passwordService = new PasswordServiceImpl();
        authService = new AuthServiceImpl(jpaUserRepository, passwordService);
        userService = new UserServiceImpl(jpaUserRepository, authService, passwordService);
        settingService = new SettingServiceImpl(audioExtractor);

        AppConfig config = new AppConfig();
        NativeLibrary.addSearchPath("libvlc", AppConfig.getVlcPath());
        System.setProperty("jna.library.path", AppConfig.getVlcPath());


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

    public static UserService getUserService() {
        return userService;
    }

    public static PasswordService getPasswordService() {
        return passwordService;
    }

    public static PlayerService getPlayerService() {
        return playerService;
    }

    public static DownloadService getDownloadService() {
        return downloadService;
    }

    public static SettingService getSettingService() {
        return settingService;
    }

    public static MetadataService getMetadataService() {
        return metadataService;
    }

    public static PlaylistService getPlaylistService() {
        return playlistService;
    }
}
