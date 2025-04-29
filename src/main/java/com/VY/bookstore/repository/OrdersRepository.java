package com.VY.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VY.bookstore.model.Orders;

/**
 * OrdersRepository – JPA‑репозиторій для роботи з сутністю {@link Orders}.
 * <p>
 * Наслідує {@link JpaRepository}, що надає базові CRUD‑операції (save, findAll, findById, delete тощо).
 * Spring Data JPA автоматично генерує необхідні SQL‑запити на основі сигнатур методів.
 */
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    /**
     * Повертає список замовлень певного користувача.
     * <p>
     * Ім'я методу відповідає конвенції Spring Data JPA: «findBy + FieldName».
     * Framework згенерує SELECT * FROM orders WHERE user_id = ?
     *
     * @param id id користувача, чиї замовлення потрібно отримати
     * @return список {@link Orders}, пов'язаних із цим користувачем
     */
    List<Orders> findByUserId(Long id);
}
