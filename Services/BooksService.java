package Services;

import Model.Books;
import Repositories.BooksRepository;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class BooksService {
    private static final String ACTIVE = "active";
    private static final String INACTIVE = "inactive";

    private final BooksRepository repository;

    public BooksService() {
        this(new BooksRepository());
    }

    public BooksService(BooksRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository không được null");
    }

    public Optional<Books> findById(String bookId) throws SQLException {
        ServiceValidator.requireText(bookId, "bookId", 20);
        return repository.findById(bookId);
    }

    public void create(Books book) throws SQLException {
        Objects.requireNonNull(book, "book không được null");
        validateBook(book);
        if (book.getStatus() == null) {
            book.setStatus(ACTIVE);
        }
        validateBookStatus(book.getStatus());
        repository.insert(book);
    }

    public boolean update(Books book) throws SQLException {
        Objects.requireNonNull(book, "book không được null");
        validateBook(book);
        validateBookStatus(book.getStatus());
        return repository.update(book);
    }

    public boolean deleteById(String bookId) throws SQLException {
        ServiceValidator.requireText(bookId, "bookId", 20);
        return repository.deleteById(bookId);
    }

    private static void validateBook(Books book) {
        ServiceValidator.requireText(book.getBookId(), "bookId", 20);
        ServiceValidator.requireText(book.getTitle(), "title", 255);
        ServiceValidator.optionalText(book.getCategoryId(), "categoryId", 10);
        ServiceValidator.optionalText(
                book.getSecondaryCategoryId(), "secondaryCategoryId", 10);
        if (book.getSecondaryCategoryId() != null
                && (book.getCategoryId() == null
                || book.getSecondaryCategoryId().equals(book.getCategoryId()))) {
            throw new IllegalArgumentException(
                    "secondaryCategoryId phải khác categoryId và cần có categoryId");
        }
        ServiceValidator.optionalText(book.getPublisherId(), "publisherId", 50);
        ServiceValidator.integerRange(book.getPublicationYear(), 0, 65535, "publicationYear");
        ServiceValidator.integerRange(book.getMinReaderAge(), 0, 255, "minReaderAge");
    }

    private static void validateBookStatus(String status) {
        ServiceValidator.oneOf(status, "status", ACTIVE, INACTIVE);
    }
}
