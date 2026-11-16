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

    public Optional<Readers> findById(String userId) throws SQLException {
        ServiceValidator.requireText(userId, "userId", 30);
        return repository.findById(userId);
    }

    public void create(Readers reader) throws SQLException {
        UsersService.validateForCreate(reader);
        validateReader(reader);
        repository.insert(reader);
    }

    public boolean update(Readers reader) throws SQLException {
        UsersService.validateForUpdate(reader);
        validateReader(reader);
        return repository.update(reader);
    }

    public boolean deleteById(String userId) throws SQLException {
        ServiceValidator.requireText(userId, "userId", 30);
        return repository.deleteById(userId);
    }

    private static void validateReader(Readers reader) {
        ServiceValidator.requireText(reader.getCitizenId(), "citizenId", 20);
        ServiceValidator.digitsOnly(reader.getCitizenId(), "citizenId");
    }
}
