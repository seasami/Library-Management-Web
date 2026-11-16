package Repositories;

import Model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepository {
    public Optional<Users> findById(String userId) throws SQLException {
        String sql = "SELECT user_id, role_id, created_at, username, email, "
                + "password_hash, full_name, phone, status FROM users WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(mapUser(resultSet))
                        : Optional.empty();
            }
        }
    }

    public List<Users> findAll() throws SQLException {
        String sql = "SELECT user_id, role_id, created_at, username, email, "
                + "password_hash, full_name, phone, status FROM users ORDER BY user_id";
        List<Users> users = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
        }
        return users;
    }

    public void insert(Users user) throws SQLException {
        String sql = "INSERT INTO users (user_id, role_id, username, email, password_hash, "
                + "full_name, phone, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            insertUser(connection, user, sql);
        }
    }

    public boolean update(Users user) throws SQLException {
        String sql = "UPDATE users SET role_id = ?, username = ?, email = ?, password_hash = ?, "
                + "full_name = ?, phone = ?, status = ? WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (!exists(connection, user.getUserId())) {
                return false;
            }
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                bindUserUpdate(statement, user);
                statement.executeUpdate();
                return true;
            }
        }
    }

    public boolean deleteById(String userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            return statement.executeUpdate() > 0;
        }
    }

    static void insertUser(Connection connection, Users user, String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserId());
            statement.setString(2, user.getRoleId());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPasswordHash());
            statement.setString(6, user.getFullName());
            statement.setString(7, user.getPhone());
            statement.setString(8, user.getStatus());
            statement.executeUpdate();
        }
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT created_at FROM users WHERE user_id = ?")) {
            statement.setString(1, user.getUserId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Timestamp createdAt = resultSet.getTimestamp("created_at");
                    if (createdAt != null) {
                        user.setCreatedAt(createdAt.toLocalDateTime());
                    }
                }
            }
        }
    }

    static void bindUserUpdate(PreparedStatement statement, Users user) throws SQLException {
        statement.setString(1, user.getRoleId());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPasswordHash());
        statement.setString(5, user.getFullName());
        statement.setString(6, user.getPhone());
        statement.setString(7, user.getStatus());
        statement.setString(8, user.getUserId());
    }

    static Users mapUser(ResultSet resultSet) throws SQLException {
        Users user = new Users();
        user.setUserId(resultSet.getString("user_id"));
        user.setRoleId(resultSet.getString("role_id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPasswordHash(resultSet.getString("password_hash"));
        user.setFullName(resultSet.getString("full_name"));
        user.setPhone(resultSet.getString("phone"));
        user.setStatus(resultSet.getString("status"));
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }
        return user;
    }

    static boolean exists(Connection connection, String userId) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT 1 FROM users WHERE user_id = ?")) {
            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
