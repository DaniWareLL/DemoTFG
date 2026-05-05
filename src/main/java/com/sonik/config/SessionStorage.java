package com.sonik.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class SessionStorage {

    private static final String FILE = "session.properties";

    public static void save(String username) {
        try (FileWriter writer = new FileWriter(FILE)) {
            writer.write("username=" + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String load() {
        try (FileReader reader = new FileReader(FILE)) {
            Properties props = new Properties();
            props.load(reader);
            return props.getProperty("username");
        } catch (IOException e) {
            return null;
        }
    }

    public static void clear() {
        new File(FILE).delete();
    }
}
