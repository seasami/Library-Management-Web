package Services;

import Model.BookCopies;
import Repositories.BookCopiesRepository;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class BookCopiesService {
    private static final String AVAILABLE = "available";
    private static final String MAINTENANCE = "maintenance";
    private static final String RETIRED = "retired";

    private final BookCopiesRepository repository;

    public BookCopiesService() {
        this(new BookCopiesRepository());
    }

    public BookCopiesService(BookCopiesRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository không được null");
    }


    public Optional<BookCopies> findById(String copyId) {
        ServiceValidator.requireText(copyId, "copyId", 5);
        try {
            return repository.findById(copyId);
        } catch (SQLException exception) {
            throw new LookupException(exception);
        }
    }

    public void create(BookCopies bookCopy) {
        Objects.requireNonNull(bookCopy, "bookCopy không được null");
        ServiceValidator.requireText(bookCopy.getCopyId(), "copyId", 5);
        ServiceValidator.requireText(bookCopy.getBookId(), "bookId", 20);
        ServiceValidator.requireText(bookCopy.getCopyCode(), "copyCode", 50);

        if (bookCopy.getCopyStatus() == null) {
            bookCopy.setCopyStatus(AVAILABLE);
        }
        validateCopyStatus(bookCopy.getCopyStatus());
        try {
            repository.insert(bookCopy);
        } catch (SQLException exception) {
            throw new CreationException(exception);
        }
    }

    public boolean update(BookCopies bookCopy) {
        Objects.requireNonNull(bookCopy, "bookCopy không được null");
        ServiceValidator.requireText(bookCopy.getCopyId(), "copyId", 5);
        ServiceValidator.requireText(bookCopy.getBookId(), "bookId", 20);
        ServiceValidator.requireText(bookCopy.getCopyCode(), "copyCode", 50);
        validateCopyStatus(bookCopy.getCopyStatus());
        try {
            return repository.update(bookCopy);
        } catch (SQLException exception) {
            throw new UpdateException(exception);
        }
    }

    public boolean deleteById(String copyId) {
        ServiceValidator.requireText(copyId, "copyId", 5);
        try {
            return repository.deleteById(copyId);
        } catch (SQLException exception) {
            throw new DeletionException(exception);
        }
    }

    private static void validateCopyStatus(String status) {
        ServiceValidator.oneOf(
                status, "copyStatus", AVAILABLE, MAINTENANCE, RETIRED);
    }

    public static class ServiceException extends RuntimeException {
        private ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static final class LookupException extends ServiceException {
        public LookupException(Throwable cause) {
            super("Không thể tìm bản sao sách", cause);
        }
    }

    public static final class CreationException extends ServiceException {
        public CreationException(Throwable cause) {
            super("Không thể tạo bản sao sách", cause);
        }
    }

    public static final class UpdateException extends ServiceException {
        public UpdateException(Throwable cause) {
            super("Không thể cập nhật bản sao sách", cause);
        }
    }

    public static final class DeletionException extends ServiceException {
        public DeletionException(Throwable cause) {
            super("Không thể xóa bản sao sách", cause);
        }
    }
}
