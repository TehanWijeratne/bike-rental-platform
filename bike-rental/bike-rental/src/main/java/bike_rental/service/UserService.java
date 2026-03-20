package bike_rental.service;

import bike_rental.model.RegularUser;
import bike_rental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String createUser(RegularUser user) {

        // Validation 1: Name must not be empty or blank
        if (user.getName() == null || user.getName().isBlank()) {
            return "Name is required";
        }

        // Validation 2: Email must contain @ symbol to be a valid email
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            return "Valid email required";
        }

        // Validation 3: Check if this user ID already exists in the database
        // If findById returns something (not null), the ID is already taken
        // Without this check, MySQL throws an ugly error for duplicate primary key
        if (userRepository.findById(user.getUserId()) != null) {
            return "User ID already exists";
        }

        // All validations passed — safe to save to database
        userRepository.save(user);
        return "success";
    }

    public List<RegularUser> getAllUsers() {
        return userRepository.findAll();
    }

    public List<RegularUser> getAllUsers(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllUsers();
        }
        return userRepository.search(keyword);
    }

    public RegularUser getUserById(String id) {
        return userRepository.findById(id);
    }

    public String updateUser(RegularUser user) {
        // Check the user actually exists before trying to update
        if (userRepository.findById(user.getUserId()) == null) {
            return "User not found";
        }
        userRepository.update(user);
        return "success";
    }

    public boolean deleteUser(String userId) {
        return userRepository.delete(userId) > 0;
    }
}