package com.martinLillo.login.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * No se usa con ORM pero nos puede servir pa algo a si q ahi se queda
 */
public class ConnectionDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/tfg_db";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    /**
     * Obtains a new and valid connection to the PostgreSQL database.
     *
     * @return an instance of {@link Connection} ready to use.
     * @throws SQLException if any issue occurs while establishing the connection
     *                      (incorrect credentials, server unavailable, etc.).
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
