package com.VY.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * CartItem – сутність, що представляє одну позицію у кошику користувача.
 * Кожен обʼєкт відповідає рядку в таблиці cart_item (або подібній) у БД.
 */
@Data
@Entity
public class CartItem {

    // =================== Первинний ключ ===================

    /**
     * Унікальний ідентифікатор позиції кошика.
     * Генерується автоматично (AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =================== Звʼязки ===================

    /**
     * Користувач, якому належить цей CartItem.
     * Звʼязок «багато‑до‑одного» (ManyToOne):
     *  – один користувач може мати багато позицій у кошику.
     *  – nullable = false – позиція не може існувати без користувача.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    /**
     * Товар, доданий до кошика.
     * Звʼязок «багато‑до‑одного»: один товар може бути у багатьох кошиках.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "productID", nullable = false)
    private Product product;

    /**
     * Замовлення, до якого вже привʼязано цю позицію (якщо оформлено).
     * Може бути null, доки користувач не оформив замовлення.
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "orderID")
    private Orders order;

    // =================== Дані позиції ===================

    /**
     * Кількість одиниць товару.
     */
    private Integer quantity;

    /**
     * Підсумкова ціна за цю позицію (quantity × price одиниці).
     */
    private Double price;
}
