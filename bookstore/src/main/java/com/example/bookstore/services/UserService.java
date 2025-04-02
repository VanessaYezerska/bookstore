package com.example.bookstore.services;

import com.example.bookstore.models.User;
import com.example.bookstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Реєстрація користувача
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // Пошук користувача за email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
