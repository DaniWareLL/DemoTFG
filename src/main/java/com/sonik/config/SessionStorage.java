package com.sonik.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Accesses the properties file containing the last user connected ("remember me" checkbox)
 */
public class SessionStorage {

    private static final String FILE = "session.properties";

    /**
     * Saves a username to {@link #FILE} for later retrieval
     * @param username The username to store
     */
    public static void save(String username) {
        try (FileWriter writer = new FileWriter(FILE)) {
            writer.write("username=" + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gives a username stored in {@link #FILE}
     * @return The username found in the file
     */
    public static String load() {
        try (FileReader reader = new FileReader(FILE)) {
            Properties props = new Properties();
            props.load(reader);
            return props.getProperty("username");
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Deletes the properties {@link #FILE file}
     */
    public static void clear() {
        new File(FILE).delete();
    }
}
