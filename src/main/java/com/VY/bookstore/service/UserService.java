package com.VY.bookstore.service;

import com.VY.bookstore.model.User;
import com.VY.bookstore.model.UserDto;

/**
 * UserService – сервісний інтерфейс для роботи з користувачами.
 * <p>
 * Визначає базові операції, необхідні для реєстрації та пошуку користувачів.
 */
public interface UserService {

    // =================== Пошук користувача ===================

    /**
     * Шукає користувача за його унікальним username.
     *
     * @param username логін користувача
     * @return об'єкт {@link User}, якщо знайдено; інакше – null
     */
    User findByUsername(String username);

    // =================== Реєстрація користувача ===================

    /**
     * Створює та зберігає нового користувача на основі даних із форми.
     *
     * @param userDto DTO, що містить дані нового користувача
     * @return збережений об'єкт {@link User}
     */
    User save(UserDto userDto);
}
