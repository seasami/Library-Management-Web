package Repositories;

import Model.BookCopies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class BookCopyRepository {
    public Optional<BookCopies> findById(String copyId) throws SQLException {
        String sql = "SELECT copy_id, book_id, copy_code, copy_status, created_at "
                + "FROM book_copies WHERE copy_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, copyId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(mapBookCopy(resultSet))
                        : Optional.empty();
            }
        }
    }

    public void insert(BookCopies bookCopy) throws SQLException {
        String sql = "INSERT INTO book_copies (copy_id, book_id, copy_code, copy_status) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bindBookCopy(statement, bookCopy);
            statement.executeUpdate();
            setGeneratedTimestamp(connection, bookCopy);
        }
    }

    public boolean update(BookCopies bookCopy) throws SQLException {
        String sql = "UPDATE book_copies SET book_id = ?, copy_code = ?, copy_status = ? "
                + "WHERE copy_id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (!exists(connection, bookCopy.getCopyId())) {
                return false;
            }
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, bookCopy.getBookId());
                statement.setString(2, bookCopy.getCopyCode());
                statement.setString(3, bookCopy.getCopyStatus());
                statement.setString(4, bookCopy.getCopyId());
                statement.executeUpdate();
                setGeneratedTimestamp(connection, bookCopy);
                return true;
            }
        }
    }

    public boolean deleteById(String copyId) throws SQLException {
        String sql = "DELETE FROM book_copies WHERE copy_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, copyId);
            return statement.executeUpdate() > 0;
        }
    }

    private static void bindBookCopy(PreparedStatement statement, BookCopies bookCopy)
            throws SQLException {
        statement.setString(1, bookCopy.getCopyId());
        statement.setString(2, bookCopy.getBookId());
        statement.setString(3, bookCopy.getCopyCode());
        statement.setString(4, bookCopy.getCopyStatus());
    }

    private static BookCopies mapBookCopy(ResultSet resultSet) throws SQLException {
        BookCopies bookCopy = new BookCopies();
        bookCopy.setCopyId(resultSet.getString("copy_id"));
        bookCopy.setBookId(resultSet.getString("book_id"));
        bookCopy.setCopyCode(resultSet.getString("copy_code"));
        bookCopy.setCopyStatus(resultSet.getString("copy_status"));
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        if (createdAt != null) {
            bookCopy.setCreatedAt(createdAt.toLocalDateTime());
        }
        return bookCopy;
    }

    private static void setGeneratedTimestamp(Connection connection, BookCopies bookCopy)
            throws SQLException {
        String sql = "SELECT created_at FROM book_copies WHERE copy_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookCopy.getCopyId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Timestamp createdAt = resultSet.getTimestamp("created_at");
                    if (createdAt != null) {
                        bookCopy.setCreatedAt(createdAt.toLocalDateTime());
                    }
                }
            }
        }
    }

    private static boolean exists(Connection connection, String copyId) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT 1 FROM book_copies WHERE copy_id = ?")) {
            statement.setString(1, copyId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
