package com.VY.bookstore.repository;

import com.VY.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository – JPA‑репозиторій для роботи з сутністю {@link User}.
 * <p>
 * Наслідує {@link JpaRepository}, що надає стандартні CRUD‑операції (save, findAll, findById, delete тощо).
 * Додає кастомний метод пошуку користувача за username.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Знаходить користувача за його унікальним username.
     * <p>
     * Spring Data JPA згенерує запит:
     * <pre>
     * SELECT * FROM user WHERE username = ?
     * </pre>
     *
     * @param username логін користувача
     * @return {@link User} або null, якщо користувача не знайдено
     */
    User findByUsername(String username);
}
