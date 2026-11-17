package Services;

import Model.Users;
import Repositories.UsersRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UsersService {
    private static final String ACTIVE = "active";
    private static final String RESTRICTED = "restricted";
    private static final String LOCKED = "locked";

    private final UsersRepository repository;

    public UsersService() {
        this(new UsersRepository());
    }

    public UsersService(UsersRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository không được null");
    }

    public Optional<Users> findById(String userId) {
        ServiceValidator.requireText(userId, "userId", 30);
        try {
            return repository.findById(userId);
        } catch (SQLException exception) {
            throw new LookupException(exception);
        }
    }

    public List<Users> findAll() {
        try {
            return repository.findAll();
        } catch (SQLException exception) {
            throw new ListRetrievalException(exception);
        }
    }

    public void create(Users user) {
        validateForCreate(user);
        try {
            repository.insert(user);
        } catch (SQLException exception) {
            throw new CreationException(exception);
        }
    }

    public boolean update(Users user) {
        validateForUpdate(user);
        try {
            return repository.update(user);
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

    static void validateForCreate(Users user) {
        Objects.requireNonNull(user, "user không được null");
        validateUser(user);
        if (user.getStatus() == null) {
            user.setStatus(ACTIVE);
        }
        validateUserStatus(user.getStatus());
    }

    static void validateForUpdate(Users user) {
        Objects.requireNonNull(user, "user không được null");
        validateUser(user);
        validateUserStatus(user.getStatus());
    }

    private static void validateUser(Users user) {
        ServiceValidator.requireText(user.getUserId(), "userId", 30);
        ServiceValidator.requireText(user.getRoleId(), "roleId", 30);
        ServiceValidator.requireText(user.getUsername(), "username", 50);
        ServiceValidator.requireText(user.getPasswordHash(), "passwordHash", 255);
        ServiceValidator.requireText(user.getFullName(), "fullName", 150);
        ServiceValidator.optionalText(user.getEmail(), "email", 255);
        if (user.getEmail() != null) {
            ServiceValidator.email(user.getEmail(), "email");
        }
        ServiceValidator.requireText(user.getPhone(), "phone", 10);
        ServiceValidator.digitsWithLeadingZero(user.getPhone(), "phone", 10);
    }

    private static void validateUserStatus(String status) {
        ServiceValidator.oneOf(status, "status", ACTIVE, RESTRICTED, LOCKED);
    }

    public static class ServiceException extends RuntimeException {
        private ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static final class LookupException extends ServiceException {
        public LookupException(Throwable cause) {
            super("Không thể tìm người dùng", cause);
        }
    }

    public static final class ListRetrievalException extends ServiceException {
        public ListRetrievalException(Throwable cause) {
            super("Không thể lấy danh sách người dùng", cause);
        }
    }

    public static final class CreationException extends ServiceException {
        public CreationException(Throwable cause) {
            super("Không thể tạo người dùng", cause);
        }
    }

    public static final class UpdateException extends ServiceException {
        public UpdateException(Throwable cause) {
            super("Không thể cập nhật người dùng", cause);
        }
    }

    public static final class DeletionException extends ServiceException {
        public DeletionException(Throwable cause) {
            super("Không thể xóa người dùng", cause);
        }
    }
}
