package Repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/quan_ly_thu_vien?useSSL=false&serverTimezone=UTC";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "";

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        String url = getEnvironmentValue("DB_URL", DEFAULT_URL);
        String username = getEnvironmentValue("DB_USER", DEFAULT_USERNAME);
        String password = System.getenv().getOrDefault("DB_PASSWORD", DEFAULT_PASSWORD);

        return DriverManager.getConnection(url, username, password);
    }

    private static String getEnvironmentValue(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.trim().isEmpty() ? defaultValue : value;
    }
}