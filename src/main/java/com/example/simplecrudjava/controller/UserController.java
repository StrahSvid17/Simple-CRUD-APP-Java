package com.example.simplecrudjava.controller;

import com.example.simplecrudjava.model.User;
import com.example.simplecrudjava.model.UserModel;
import com.example.simplecrudjava.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/all")
    public List<User> getAll() {
        return service.getAllUsers();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        return service.addNewUser(user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> dropUser(Long id) {
        return service.deleteUser(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserModel user) {
        return service.updateUser(user);
    }
}
