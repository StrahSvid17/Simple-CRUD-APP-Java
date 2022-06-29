package com.example.simplecrudjava.service;

import com.example.simplecrudjava.model.User;
import com.example.simplecrudjava.model.UserModel;
import com.example.simplecrudjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<?> addNewUser(User user) {
        try {
            userRepository.save(user);
            return ResponseEntity.ok("User has been created.");
        } catch (DataIntegrityViolationException e) {
            boolean isContainsUsername = Objects.requireNonNull(e.getRootCause()).getMessage().contains("username");
            return ResponseEntity.badRequest().body
                    (String.format("This %s already exists.", isContainsUsername ? "username" : "email"));
        }
    }

    public ResponseEntity<?> deleteUser(Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User has been deleted.");
    }

    public ResponseEntity<?> updateUser(UserModel userModel) {
        try {
            User user = userRepository.findById(userModel.getId()).orElseThrow();
            if (userModel.getUsername() == null)
                userModel.setUsername(user.getUsername());
            if (userModel.getEmail() == null)
                userModel.setEmail(user.getEmail());
            if (userModel.getAddress() == null)
                userModel.setAddress(user.getAddress());

            userRepository.updateUser(userModel.getUsername(), userModel.getEmail(), userModel.getId());
            return ResponseEntity.ok("User has been updated.");

        } catch (DataIntegrityViolationException e) {
            boolean isContainsUsername = Objects.requireNonNull(e.getRootCause()).getMessage().contains("username");
            return ResponseEntity.badRequest().body(String.format
                    ("This %s already exists.", isContainsUsername ? "username" : "email"));
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
