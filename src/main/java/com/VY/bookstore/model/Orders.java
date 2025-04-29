package com.VY.bookstore.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

/**
 * Orders – сутність, що представляє замовлення користувача.
 * Містить посилання на товари у кошику, користувача‑власника та метадані (сума, дата).
 */
@Data
@Entity
public class Orders {

    // =================== Первинний ключ ===================

    /**
     * Унікальний ідентифікатор замовлення.
     * Генерується БД автоматично (AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =================== Звʼязки ===================

    /**
     * Позиції кошика, що увійшли до замовлення.
     * Звʼязок «один‑до‑багатьох» (OneToMany),
     * поле cartItem.order у класі CartItem є зворотним боком цього звʼязку.
     */
    @OneToMany(mappedBy = "order")
    private List<CartItem> cart;

    /**
     * Користувач, який оформив це замовлення.
     * nullable = false – замовлення не може існувати без власника.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    // =================== Атрибути замовлення ===================

    /**
     * Загальна сума замовлення.
     */
    private Double totalprice;

    /**
     * Дата оформлення замовлення.
     */
    private LocalDate orderdate;
}
