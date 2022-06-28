package com.example.simplecrudjava.service;

import com.example.simplecrudjava.model.User;
import com.example.simplecrudjava.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<?> addNewUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            return ResponseEntity.badRequest().body("This username already exists.");
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            return ResponseEntity.badRequest().body("This email already exists.");

        userRepository.save(user);
        return ResponseEntity.ok("User has been created.");
    }

    public ResponseEntity<?> deleteUser(Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User has been deleted.");
    }

    public ResponseEntity<?> updateUser(UserModel userModel) {
        User user = userRepository.findById(userModel.getId()).orElseThrow();

        Optional<User> checkEmail = userRepository.findByEmail(userModel.getEmail());
        Optional<User> checkUsername = userRepository.findByEmail(userModel.getUsername());

        if (checkEmail.isPresent()
                && !(checkEmail.get().getEmail().equals(userModel.getEmail())))
            return ResponseEntity.badRequest().body("This email already exists.");
        if (checkUsername.isPresent()
                && !(checkUsername.get().getUsername().equals(userModel.getUsername())))
            return ResponseEntity.badRequest().body("This username already exists.");

        if (userModel.getUsername() == null)
            userModel.setUsername(user.getUsername());
        if (userModel.getEmail() == null)
            userModel.setEmail(user.getEmail());

        userRepository.updateUser(
                userModel.getUsername(),
                userModel.getEmail(),
                userModel.getId());
        return ResponseEntity.ok("User has been updated");
    }
}

@Data
class UserModel {
    private Long id;
    private String username;
    private String email;
}
