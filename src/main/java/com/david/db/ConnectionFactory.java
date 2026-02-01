package com.david.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {

    private ConnectionFactory() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DbConfig.URL,
                DbConfig.USER,
                DbConfig.PASSWORD
        );
    }
}
