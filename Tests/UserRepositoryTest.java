package Tests;

import Model.Users;
import Repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepositoryTest {
    public static void main(String[] args) throws SQLException {
        UserRepository repository = new UserRepository();
        List<Users> users = repository.findAll();

        System.out.println("Danh sách user (" + users.size() + "):");
        for (Users user : users) {
            System.out.printf("ID: %s | Username: %s | Họ tên: %s | Role: %s | Trạng thái: %s%n",
                    user.getUserId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getRoleId(),
                    user.getStatus());
        }

        if (args.length > 0) {
            printUserById(repository, args[0]);
        } else if (!users.isEmpty()) {
            printUserById(repository, users.get(0).getUserId());
        } else {
            System.out.println("Database chưa có user để kiểm tra tìm theo ID.");
        }
    }

    private static void printUserById(UserRepository repository, String userId)
            throws SQLException {
        Optional<Users> result = repository.findById(userId);
        if (result.isPresent()) {
            Users user = result.get();
            System.out.printf("%nTìm thấy user theo ID:%n"
                            + "ID: %s | Username: %s | Họ tên: %s | Role: %s | Trạng thái: %s%n",
                    user.getUserId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getRoleId(),
                    user.getStatus());
        } else {
            System.out.println("\nKhông tìm thấy user có ID: " + userId);
        }
    }
}
