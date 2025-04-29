package com.VY.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VY.bookstore.model.CartItem;

/**
 * CartRepository – репозиторій JPA для роботи з сутністю {@link CartItem}.
 * <p>
 * Наслідує базові CRUD‑операції від {@link JpaRepository} та містить
 * кастомні методи пошуку, необхідні бізнес‑логіці застосунку.
 */
public interface CartRepository extends JpaRepository<CartItem, Long> {

    // =================== Кастомні запити ===================

    /**
     * Знаходить CartItem за його первинним ключем (id).
     * <p>
     * Повертає null, якщо запис не знайдено.
     *
     * @param id ідентифікатор позиції кошика
     * @return CartItem або null
     */
    public CartItem findById(Integer id);

    /**
     * Пошук позиції кошика за поєднанням productId, userId та orderId.
     * <p>
     * Використовується, щоб перевірити, чи товар уже доданий у кошик користувача
     * і ще не привʼязаний до певного замовлення.
     *
     * @param productID id товару
     * @param userID    id користувача
     * @param orderID   id замовлення (може бути null для активного кошика)
     * @return CartItem або null, якщо нічого не знайдено
     */
    public CartItem findByProductIdAndUserIdAndOrderId(Long productID, Long userID, Long orderID);

    /**
     * Повертає усі позиції кошика для конкретного користувача.
     * <p>
     * Використовується для відображення вмісту кошика.
     *
     * @param userID id користувача
     * @return список CartItem цього користувача
     */
    public List<CartItem> findByUserId(Long userID);
}
