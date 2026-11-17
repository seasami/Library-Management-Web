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
        } catch (Exception ex) {
            System.out.println("Lỗi khi tìm bản sao sách " + copyId + ": " + ex.getMessage());
            return Optional.empty();
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
            System.out.println("Tạo bản sao sách thành công với Copy ID: " + bookCopy.getCopyId());
        } catch (SQLException exception) {
            System.out.println("Lỗi khi tạo bản sao sách " + bookCopy.getCopyId() + ": " + exception.getMessage());
        }
    }

    public void update(BookCopies bookCopy) {
        Objects.requireNonNull(bookCopy, "bookCopy không được null");
        try {
            if (!repository.findById(bookCopy.getCopyId()).isPresent()) {
                System.out.println("Không tìm thấy bản sao sách với Copy ID: " + bookCopy.getCopyId());
                return;
            }
        } catch (Exception exception) {
            System.out.println("Lỗi khi tìm bản sao sách " + bookCopy.getCopyId() + ": " + exception.getMessage());
            return;
        }
        ServiceValidator.requireText(bookCopy.getBookId(), "bookId", 20);
        ServiceValidator.requireText(bookCopy.getCopyCode(), "copyCode", 50);
        validateCopyStatus(bookCopy.getCopyStatus());
        try {
            repository.update(bookCopy);
            System.out.println("Cập nhật bản sao sách thành công với Copy ID: " + bookCopy.getCopyId());
        } catch (SQLException exception) {
            System.out.println("Lỗi khi cập nhật bản sao sách " + bookCopy.getCopyId() + ": " + exception.getMessage());
        }
    }

    public void deleteById(String copyId) {
        ServiceValidator.requireText(copyId, "copyId", 5);
        try {
            if (!repository.findById(copyId).isPresent()) {
                throw new IllegalArgumentException("Bản sao sách không tồn tại.");
            }
            repository.deleteById(copyId);
            System.out.println("Xóa bản sao sách thành công với Copy ID: " + copyId);
        } catch (SQLException exception) {
            System.out.println("Lỗi khi xóa bản sao sách " + copyId + ": " + exception.getMessage());
        }
    }

    private static void validateCopyStatus(String status) {
        ServiceValidator.oneOf(
                status, "copyStatus", AVAILABLE, MAINTENANCE, RETIRED);
    }
}   