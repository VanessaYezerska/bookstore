package com.VY.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.VY.bookstore.model.Product;

import java.util.List;

/**
 * ProductRepository – JPA‑репозиторій для роботи з сутністю {@link Product}.
 * <p>
 * Наслідує {@link JpaRepository}, що надає стандартні CRUD‑операції.
 * Також містить кастомні методи пошуку, які Spring Data JPA реалізує автоматично
 * на основі їхніх назв.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    // =================== Кастомні методи пошуку ===================

    /**
     * Повертає всі книги, позначені як бестселери (isBestseller = true).
     *
     * @return список бестселерів
     */
    List<Product> findByIsBestsellerTrue();

    /**
     * Повертає всі книги, позначені як новинки (isNewArrival = true).
     *
     * @return список новинок
     */
    List<Product> findByIsNewArrivalTrue();

    /**
     * Пошук книг за частковим збігом у назві або авторі (регістр не враховується).
     * <p>
     * SQL, який згенерує Spring Data JPA, буде приблизно такий:
     * <pre>
     * SELECT * FROM product
     * WHERE LOWER(title) LIKE LOWER('%?%')
     *    OR LOWER(author) LIKE LOWER('%?%');
     * </pre>
     *
     * @param title   підрядок для пошуку у назві
     * @param author  підрядок для пошуку в авторі
     * @return список книг, що відповідають критеріям
     */
    List<Product> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
}
