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

    public Optional<Users> findById(String userId) throws SQLException {
        ServiceValidator.requireText(userId, "userId", 30);
        return repository.findById(userId);
    }

    public List<Users> findAll() throws SQLException {
        return repository.findAll();
    }

    public void create(Users user) throws SQLException {
        validateForCreate(user);
        repository.insert(user);
    }

    public boolean update(Users user) throws SQLException {
        validateForUpdate(user);
        return repository.update(user);
    }

    public boolean deleteById(String userId) throws SQLException {
        ServiceValidator.requireText(userId, "userId", 30);
        return repository.deleteById(userId);
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
        ServiceValidator.requireText(user.getPhone(), "phone", 10);
        ServiceValidator.digitsOnly(user.getPhone(), "phone");
    }

    private static void validateUserStatus(String status) {
        ServiceValidator.oneOf(status, "status", ACTIVE, RESTRICTED, LOCKED);
    }
}
