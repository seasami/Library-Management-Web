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
            System.out.println("Tìm thấy sách với ID: " + bookId);
            return repository.findById(bookId);
        } catch (SQLException exception) {
            System.err.println("Lỗi khi tìm sách với ID: " + bookId);
            return Optional.empty();
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
            System.out.println("Sách đã được tạo thành công với ID: " + book.getBookId());
        } catch (SQLException exception) {
            System.err.println("Lỗi khi tạo sách với ID: " + book.getBookId());
        }
    }

    public void update(Books book) {
        Objects.requireNonNull(book, "book không được null");
        validateBook(book);
        validateBookStatus(book.getStatus());
        try {
            repository.update(book);
            System.out.println("Sách đã được cập nhật thành công với ID: " + book.getBookId());
        } catch (SQLException exception) {
            System.err.println("Lỗi khi cập nhật sách với ID: " + book.getBookId());
        }
    }

    public void deleteById(String bookId) {
        ServiceValidator.requireText(bookId, "bookId", 20);
        try {
            repository.deleteById(bookId);
            System.out.println("Sách đã được xóa thành công với ID: " + bookId);
        } catch (SQLException exception) {
            System.err.println("Lỗi khi xóa sách với ID: " + bookId);
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
}
