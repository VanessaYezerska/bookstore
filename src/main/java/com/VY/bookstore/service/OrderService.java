package com.VY.bookstore.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.VY.bookstore.model.CartItem;
import com.VY.bookstore.model.Orders;
import com.VY.bookstore.model.User;
import com.VY.bookstore.repository.OrdersRepository;

/**
 * OrderService – сервіс, що відповідає за створення замовлень та їх отримання.
 */
@Service
public class OrderService {

    /** Репозиторій для взаємодії з таблицею orders. */
    @Autowired
    private OrdersRepository ordersRepository;

    // =================== Створення замовлення ===================

    /**
     * Створює нове замовлення на основі позицій кошика користувача.
     * <ol>
     *   <li>Створює об'єкт {@link Orders} і заповнює базові поля.</li>
     *   <li>Прив'язує кожен {@link CartItem} до цього замовлення.</li>
     *   <li>Підраховує загальну вартість.</li>
     *   <li>Зберігає замовлення у базі.</li>
     * </ol>
     *
     * @param cart список позицій кошика
     * @param user користувач, що оформлює замовлення
     * @return створений та збережений {@link Orders}
     */
    public Orders createOrder(List<CartItem> cart, User user) {
        Double total = 0.0; // Підсумкова вартість

        // 1. Формуємо об'єкт замовлення
        Orders order = new Orders();
        order.setCart(cart);
        order.setUser(user);
        order.setOrderdate(LocalDate.now());

        // 2. Прив'язуємо позиції кошика та рахуємо суму
        for (CartItem item : cart) {
            item.setOrder(order);   // зворотний зв'язок many‑to‑one
            total += item.getPrice();
        }

        // 3. Встановлюємо суму та зберігаємо
        order.setTotalprice(total);
        ordersRepository.save(order);

        return order;
    }

    // =================== Отримання замовлень користувача ===================

    /**
     * Повертає всі замовлення конкретного користувача.
     *
     * @param user користувач, чиї замовлення необхідно отримати
     * @return список {@link Orders}
     */
    public List<Orders> viewOrders(User user) {
        return ordersRepository.findByUserId(user.getId());
    }
}
