package com.example.bookstore.controllers;

import com.example.bookstore.models.User;
import com.example.bookstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Реєстрація користувача
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body("Користувач з таким email вже існує.");
        }
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }


    // Вхід користувача
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(existingUser.get());
        } else {
            return ResponseEntity.status(401).build(); // Невірні дані
        }
    }
}
