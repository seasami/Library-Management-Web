package Repositories;

import Model.Books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class BookRepository {
    public Optional<Books> findById(String bookId) throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id, publication_year, "
                + "min_reader_age, status, description, created_at, updated_at "
                + "FROM books WHERE book_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(mapBook(resultSet))
                        : Optional.empty();
            }
        }
    }

    public void insert(Books book) throws SQLException {
        String sql = "INSERT INTO books (book_id, title, category_id, publisher_id, "
                + "publication_year, min_reader_age, status, description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bindBook(statement, book);
            statement.executeUpdate();
            setGeneratedTimestamps(connection, book);
        }
    }

    public boolean update(Books book) throws SQLException {
        String sql = "UPDATE books SET title = ?, category_id = ?, publisher_id = ?, "
                + "publication_year = ?, min_reader_age = ?, status = ?, description = ? "
                + "WHERE book_id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (!exists(connection, book.getBookId())) {
                return false;
            }
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getCategoryId());
                statement.setString(3, book.getPublisherId());
                setInteger(statement, 4, book.getPublicationYear());
                statement.setInt(5, book.getMinReaderAge() == null ? 0 : book.getMinReaderAge());
                statement.setString(6, book.getStatus());
                statement.setString(7, book.getDescription());
                statement.setString(8, book.getBookId());
                statement.executeUpdate();
                setGeneratedTimestamps(connection, book);
                return true;
            }
        }
    }

    public boolean deleteById(String bookId) throws SQLException {
        String sql = "DELETE FROM books WHERE book_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bookId);
            return statement.executeUpdate() > 0;
        }
    }

    private static void bindBook(PreparedStatement statement, Books book) throws SQLException {
        statement.setString(1, book.getBookId());
        statement.setString(2, book.getTitle());
        statement.setString(3, book.getCategoryId());
        statement.setString(4, book.getPublisherId());
        setInteger(statement, 5, book.getPublicationYear());
        statement.setInt(6, book.getMinReaderAge() == null ? 0 : book.getMinReaderAge());
        statement.setString(7, book.getStatus());
        statement.setString(8, book.getDescription());
    }

    private static void setInteger(PreparedStatement statement, int index, Integer value)
            throws SQLException {
        if (value == null) {
            statement.setNull(index, java.sql.Types.INTEGER);
        } else {
            statement.setInt(index, value);
        }
    }

    private static Books mapBook(ResultSet resultSet) throws SQLException {
        Books book = new Books();
        book.setBookId(resultSet.getString("book_id"));
        book.setTitle(resultSet.getString("title"));
        book.setCategoryId(resultSet.getString("category_id"));
        book.setPublisherId(resultSet.getString("publisher_id"));
        int publicationYear = resultSet.getInt("publication_year");
        if (!resultSet.wasNull()) {
            book.setPublicationYear(publicationYear);
        }
        book.setMinReaderAge(resultSet.getInt("min_reader_age"));
        book.setStatus(resultSet.getString("status"));
        book.setDescription(resultSet.getString("description"));
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        if (createdAt != null) {
            book.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        if (updatedAt != null) {
            book.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return book;
    }

    private static void setGeneratedTimestamps(Connection connection, Books book)
            throws SQLException {
        String sql = "SELECT created_at, updated_at FROM books WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getBookId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Timestamp createdAt = resultSet.getTimestamp("created_at");
                    if (createdAt != null) {
                        book.setCreatedAt(createdAt.toLocalDateTime());
                    }
                    Timestamp updatedAt = resultSet.getTimestamp("updated_at");
                    if (updatedAt != null) {
                        book.setUpdatedAt(updatedAt.toLocalDateTime());
                    }
                }
            }
        }
    }

    private static boolean exists(Connection connection, String bookId) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT 1 FROM books WHERE book_id = ?")) {
            statement.setString(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
