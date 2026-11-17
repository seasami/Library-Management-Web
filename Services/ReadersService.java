package Services;

import Model.Readers;
import Repositories.ReaderRepository;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class ReadersService {
    private final ReaderRepository repository;

    public ReadersService() {
        this(new ReaderRepository());
    }

    public ReadersService(ReaderRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository không được null");
    }

    public Optional<Readers> findById(String userId) {
        ServiceValidator.requireText(userId, "userId", 30);
        try {
            return repository.findById(userId);
        } catch (SQLException exception) {
            throw new LookupException(exception);
        }
    }

    public void create(Readers reader) {
        UsersService.validateForCreate(reader);
        validateReader(reader);
        try {
            repository.insert(reader);
        } catch (SQLException exception) {
            throw new CreationException(exception);
        }
    }

    public boolean update(Readers reader) {
        UsersService.validateForUpdate(reader);
        validateReader(reader);
        try {
            return repository.update(reader);
        } catch (SQLException exception) {
            throw new UpdateException(exception);
        }
    }

    public boolean deleteById(String userId) {
        ServiceValidator.requireText(userId, "userId", 30);
        try {
            return repository.deleteById(userId);
        } catch (SQLException exception) {
            throw new DeletionException(exception);
        }
    }

    private static void validateReader(Readers reader) {
        ServiceValidator.requireText(reader.getCitizenId(), "citizenId", 12);
        ServiceValidator.digitsWithLeadingZero(reader.getCitizenId(), "citizenId", 12);
    }

    public static class ServiceException extends RuntimeException {
        private ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static final class LookupException extends ServiceException {
        public LookupException(Throwable cause) {
            super("Không thể tìm độc giả", cause);
        }
    }

    public static final class CreationException extends ServiceException {
        public CreationException(Throwable cause) {
            super("Không thể tạo độc giả", cause);
        }
    }

    public static final class UpdateException extends ServiceException {
        public UpdateException(Throwable cause) {
            super("Không thể cập nhật độc giả", cause);
        }
    }

    public static final class DeletionException extends ServiceException {
        public DeletionException(Throwable cause) {
            super("Không thể xóa độc giả", cause);
        }
    }
}
