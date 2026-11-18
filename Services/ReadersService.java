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
            System.err.println("Lỗi khi tìm kiếm độc giả với ID: " + exception.getMessage());
            return Optional.empty();
        }
    }

    public void create(Readers reader) {
        UsersService.validateForCreate(reader);
        validateReader(reader);
        try {
            repository.insert(reader);
        } catch (SQLException exception) {
            System.err.println("Lỗi khi tạo độc giả với ID: " + exception.getMessage());
        }
    }

    public void update(Readers reader) {
        UsersService.validateForUpdate(reader);
        validateReader(reader);
        try {
            repository.update(reader);
        } catch (SQLException exception) {
            System.err.println("Lỗi khi cập nhật độc giả với ID: " + exception.getMessage());
        }
    }

    public void deleteById(String userId) {
        ServiceValidator.requireText(userId, "userId", 30);
        try {
            repository.deleteById(userId);
        } catch (SQLException exception) {
            System.err.println("Lỗi khi xóa độc giả với ID: " + exception.getMessage());
        }
    }

    private static void validateReader(Readers reader) {
        ServiceValidator.requireText(reader.getCitizenId(), "citizenId", 12);
        ServiceValidator.digitsWithLeadingZero(reader.getCitizenId(), "citizenId", 12);
    }
}
