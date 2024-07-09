package com.pleshkov.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManagerImpl implements ConnectionManager {

    public static final String DB_URL = getProperties("url");
    public static final String DB_USER = getProperties("username");
    public static final String DB_PASSWORD = getProperties("password");
    public static final String DB_DRIVER = getProperties("driver");


    public static String getProperties(String key) {
        Properties properties = new Properties();
        try (InputStream inputStream = ConnectionManagerImpl.class.getClassLoader().getResourceAsStream("database.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(key);
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(ConnectionManagerImpl.DB_DRIVER);
            connection = DriverManager.getConnection(ConnectionManagerImpl.DB_URL, ConnectionManagerImpl.DB_USER,
                    ConnectionManagerImpl.DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
