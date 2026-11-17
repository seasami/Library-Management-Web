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

    public Optional<Books> findById(String bookId) {
        ServiceValidator.requireText(bookId, "bookId", 20);
        try {
            return repository.findById(bookId);
        } catch (SQLException exception) {
            throw new LookupException(exception);
        }
    }

    public void create(Books book) {
        Objects.requireNonNull(book, "book không được null");
        validateBook(book);
        if (book.getStatus() == null) {
            book.setStatus(ACTIVE);
        }
        validateBookStatus(book.getStatus());
        try {
            repository.insert(book);
        } catch (SQLException exception) {
            throw new CreationException(exception);
        }
    }

    public boolean update(Books book) {
        Objects.requireNonNull(book, "book không được null");
        validateBook(book);
        validateBookStatus(book.getStatus());
        try {
            return repository.update(book);
        } catch (SQLException exception) {
            throw new UpdateException(exception);
        }
    }

    public boolean deleteById(String bookId) {
        ServiceValidator.requireText(bookId, "bookId", 20);
        try {
            return repository.deleteById(bookId);
        } catch (SQLException exception) {
            throw new DeletionException(exception);
        }
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
        ServiceValidator.integerRange(book.getPublicationYear(), 0, 3000, "publicationYear");
        ServiceValidator.integerRange(book.getMinReaderAge(), 0, 100, "minReaderAge");
    }

    private static void validateBookStatus(String status) {
        ServiceValidator.oneOf(status, "status", ACTIVE, INACTIVE);
    }

    public static class ServiceException extends RuntimeException {
        private ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static final class LookupException extends ServiceException {
        public LookupException(Throwable cause) {
            super("Không thể tìm sách", cause);
        }
    }

    public static final class CreationException extends ServiceException {
        public CreationException(Throwable cause) {
            super("Không thể tạo sách", cause);
        }
    }

    public static final class UpdateException extends ServiceException {
        public UpdateException(Throwable cause) {
            super("Không thể cập nhật sách", cause);
        }
    }

    public static final class DeletionException extends ServiceException {
        public DeletionException(Throwable cause) {
            super("Không thể xóa sách", cause);
        }
    }
}
