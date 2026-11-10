package Repositories;

import Model.Reader;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class ReaderRepository {
    private static final String INSERT_USER_SQL =
            "INSERT INTO users (user_id, role_id, username, email, password_hash, "
                    + "full_name, phone, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public Optional<Reader> findById(String userId) throws SQLException {
        String sql = "SELECT u.user_id, u.role_id, u.created_at, u.username, "
                + "u.email, u.password_hash, u.full_name, u.phone, u.status, r.subscription_id, "
                + "r.date_of_birth, r.updated_at, r.citizen_id "
                + "FROM users u JOIN readers r ON r.user_id = u.user_id WHERE u.user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                Reader reader = new Reader();
                copyUserFields(resultSet, reader);
                Date dateOfBirth = resultSet.getDate("date_of_birth");
                if (dateOfBirth != null) {
                    reader.setDob(dateOfBirth.toLocalDate());
                }
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                if (createdAt != null) {
                    reader.setCreatedAt(createdAt.toLocalDateTime());
                }
                Timestamp updatedAt = resultSet.getTimestamp("updated_at");
                if (updatedAt != null) {
                    reader.setUpdatedAt(updatedAt.toLocalDateTime());
                }
                reader.setCitizenId(resultSet.getString("citizen_id"));
                return Optional.of(reader);
            }
        }
    }

    public void insert(Reader reader) throws SQLException {
        String insertReaderSql = "INSERT INTO readers "
                + "(user_id, subscription_id, date_of_birth, citizen_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                UserRepository.insertUser(connection, reader, INSERT_USER_SQL);
                try (PreparedStatement statement = connection.prepareStatement(insertReaderSql)) {
                    statement.setString(1, reader.getUserId());
                    statement.setString(2, reader.getSubscriptionId());
                    setDate(statement, 3, reader.getDob());
                    statement.setString(4, reader.getCitizenId());
                    statement.executeUpdate();
                }
                connection.commit();
            } catch (SQLException exception) {
                rollback(connection, exception);
                throw exception;
            }
        }
    }

    public boolean update(Reader reader) throws SQLException {
        String updateReaderSql = "UPDATE readers SET subscription_id = ?, date_of_birth = ?, "
                + "citizen_id = ? WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                if (!readerExists(connection, reader.getUserId())) {
                    connection.rollback();
                    return false;
                }

                String updateUserSql = "UPDATE users SET role_id = ?, username = ?, email = ?, "
                        + "password_hash = ?, full_name = ?, phone = ?, "
                        + "status = ? WHERE user_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(updateUserSql)) {
                    UserRepository.bindUserUpdate(statement, reader);
                    statement.executeUpdate();
                }

                try (PreparedStatement statement = connection.prepareStatement(updateReaderSql)) {
                    statement.setString(1, reader.getSubscriptionId());
                    setDate(statement, 2, reader.getDob());
                    statement.setString(3, reader.getCitizenId());
                    statement.setString(4, reader.getUserId());
                    statement.executeUpdate();
                }
                connection.commit();
                return true;
            } catch (SQLException exception) {
                rollback(connection, exception);
                throw exception;
            }
        }
    }

    public boolean deleteById(String userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ? AND EXISTS "
                + "(SELECT 1 FROM readers WHERE readers.user_id = users.user_id)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            return statement.executeUpdate() > 0;
        }
    }

    private static void copyUserFields(ResultSet resultSet, Reader reader) throws SQLException {
        reader.setUserId(resultSet.getString("user_id"));
        reader.setRoleId(resultSet.getString("role_id"));
        reader.setSubscriptionId(resultSet.getString("subscription_id"));
        reader.setUsername(resultSet.getString("username"));
        reader.setEmail(resultSet.getString("email"));
        reader.setPasswordHash(resultSet.getString("password_hash"));
        reader.setFullName(resultSet.getString("full_name"));
        reader.setPhone(resultSet.getString("phone"));
        reader.setStatus(resultSet.getString("status"));
    }

    private static boolean readerExists(Connection connection, String userId) throws SQLException {
        String sql = "SELECT 1 FROM readers WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private static void setDate(PreparedStatement statement, int index, java.time.LocalDate date)
            throws SQLException {
        if (date == null) {
            statement.setNull(index, java.sql.Types.DATE);
        } else {
            statement.setDate(index, Date.valueOf(date));
        }
    }

    private static void rollback(Connection connection, SQLException exception) {
        try {
            connection.rollback();
        } catch (SQLException rollbackException) {
            exception.addSuppressed(rollbackException);
        }
    }
}
