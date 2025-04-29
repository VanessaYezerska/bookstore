package com.VY.bookstore.service;

import com.VY.bookstore.model.User;
import com.VY.bookstore.model.UserDto;
import com.VY.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl – реалізація інтерфейсу {@link UserService}.
 * <p>
 * Містить бізнес‑логіку для реєстрації та пошуку користувачів,
 * інкапсулюючи взаємодію з {@link UserRepository} та шифрування паролів.
 */
@Service
public class UserServiceImpl implements UserService {

    // =================== Залежності ===================

    /**
     * Кодувальник паролів (BCrypt). Використовується для безпечного зберігання паролів.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Репозиторій для доступу до таблиці users.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Конструктор‑інʼєкція (корисно для unit‑тестів).
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // =================== Пошук користувача ===================

    /** {@inheritDoc} */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // =================== Збереження користувача ===================

    /** {@inheritDoc} */
    @Override
    public User save(UserDto userDto) {
        // 1. Формуємо новий об'єкт User із даних форми
        User user = new User(
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()), // хешуємо пароль
                userDto.getFullname(),
                userDto.getEmail(),
                userDto.getAddress(),
                userDto.getCardDetails()
        );

        // 2. Зберігаємо у БД та повертаємо
        return userRepository.save(user);
    }
}
