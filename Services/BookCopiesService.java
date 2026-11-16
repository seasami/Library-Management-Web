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


    public Optional<BookCopies> findById(String copyId) throws SQLException {
        ServiceValidator.requireText(copyId, "copyId", 5);
        return repository.findById(copyId);
    }

    public void create(BookCopies bookCopy) throws SQLException {
        Objects.requireNonNull(bookCopy, "bookCopy không được null");
        ServiceValidator.requireText(bookCopy.getCopyId(), "copyId", 5);
        ServiceValidator.requireText(bookCopy.getBookId(), "bookId", 20);
        ServiceValidator.requireText(bookCopy.getCopyCode(), "copyCode", 50);

        if (bookCopy.getCopyStatus() == null) {
            bookCopy.setCopyStatus(AVAILABLE);
        }
        validateCopyStatus(bookCopy.getCopyStatus());
        repository.insert(bookCopy);
    }

    public boolean update(BookCopies bookCopy) throws SQLException {
        Objects.requireNonNull(bookCopy, "bookCopy không được null");
        ServiceValidator.requireText(bookCopy.getCopyId(), "copyId", 5);
        ServiceValidator.requireText(bookCopy.getBookId(), "bookId", 20);
        ServiceValidator.requireText(bookCopy.getCopyCode(), "copyCode", 50);
        validateCopyStatus(bookCopy.getCopyStatus());
        return repository.update(bookCopy);
    }

    public boolean deleteById(String copyId) throws SQLException {
        ServiceValidator.requireText(copyId, "copyId", 5);
        return repository.deleteById(copyId);
    }

    private static void validateCopyStatus(String status) {
        ServiceValidator.oneOf(
                status, "copyStatus", AVAILABLE, MAINTENANCE, RETIRED);
    }
}
