package com.VY.bookstore.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.VY.bookstore.model.CartItem;
import com.VY.bookstore.model.Product;
import com.VY.bookstore.model.User;
import com.VY.bookstore.repository.CartRepository;
import com.VY.bookstore.repository.ProductRepository;
import com.VY.bookstore.repository.UserRepository;

/**
 * CartService – сервісна логіка для роботи з кошиком користувача.
 * <p>
 * Інкапсулює взаємодію з репозиторіями та містить бізнес‑правила:
 *  – додавання товару до кошика;
 *  – оновлення кількості;
 *  – видалення позиції;
 *  – отримання списків позицій.
 */
@Service
public class CartService {

    // =================== Репозиторії ===================

    /** Репозиторій для операцій із CartItem. */
    @Autowired
    private CartRepository cartRepository;

    /** Репозиторій для операцій із Product. */
    @Autowired
    private ProductRepository productRepository;

    /** Репозиторій для операцій із User. */
    @Autowired
    private UserRepository userRepository;

    /** Логер для зручного дебагу та моніторингу. */
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    // =================== Оновлення кількості ===================

    /**
     * Змінює кількість товару у кошику (increase / decrease).
     * Якщо після зменшення кількість <= 0 – позицію видаляємо.
     *
     * @param id     id CartItem
     * @param action "increase" або "decrease"
     */
    public void updateQuantity(Long id, String action) {
        // 1. Отримуємо позицію кошика
        CartItem cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            logger.warn("CartItem {} not found", id);
            return;
        }

        // 2. Отримуємо товар (для ціни)
        Product product = productRepository.findById(cart.getProduct().getId()).orElse(null);
        if (product == null) {
            logger.warn("Product {} not found for CartItem {}", cart.getProduct().getId(), id);
            return;
        }

        int newQty = cart.getQuantity();

        if ("decrease".equalsIgnoreCase(action)) {
            newQty--;
            if (newQty <= 0) {
                // Видаляємо позицію, якщо кількість стала 0
                cartRepository.delete(cart);
                logger.info("CartItem {} removed (qty <= 0)", id);
                return;
            }
        } else {
            // increase
            newQty++;
        }

        // 3. Оновлюємо кількість та ціну
        cart.setQuantity(newQty);
        cart.setPrice(product.getListPrice() * newQty);
        cartRepository.save(cart);
        logger.info("CartItem {} quantity updated to {}", id, newQty);
    }

    // =================== Читання даних ===================

    /**
     * Повертає усі позиції кошика у системі (для адмін‑панелі).
     */
    public List<CartItem> getAllCarts() {
        return cartRepository.findAll();
    }

    /**
     * Повертає позиції кошика конкретного користувача.
     *
     * @param userId id користувача
     * @return список CartItem
     */
    public List<CartItem> getCartbyUser(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // =================== Додавання до кошика ===================

    /**
     * Додає товар до кошика користувача.
     * Якщо така позиція вже існує (productId + userId + orderId) – збільшуємо кількість.
     *
     * @param productID id товару
     * @param userID    id користувача
     * @param orderID   id замовлення (null для активного кошика)
     * @return оновлений CartItem
     */
    public CartItem addtoCart(Long productID, Long userID, Long orderID) {
        // 1. Отримуємо сутності
        Product product = productRepository.findById(productID).orElse(null);
        User user = userRepository.findById(userID).orElse(null);
        if (product == null || user == null) {
            logger.warn("Product {} or User {} not found", productID, userID);
            return null;
        }

        // 2. Перевіряємо, чи позиція вже існує
        CartItem existing = cartRepository.findByProductIdAndUserIdAndOrderId(productID, userID, orderID);
        CartItem cart;

        if (ObjectUtils.isEmpty(existing)) {
            // Створюємо нову позицію
            cart = new CartItem();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(1);
            cart.setPrice(product.getListPrice());
            logger.info("Created new CartItem for user {} and product {}", userID, productID);
        } else {
            // Збільшуємо кількість
            cart = existing;
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setPrice(cart.getPrice() + product.getListPrice());
            logger.info("Updated quantity for existing CartItem {}", cart.getId());
        }

        return cartRepository.save(cart);
    }

    // =================== Видалення з кошика ===================

    /**
     * Видаляє позицію кошика за id.
     */
    public void deleteCart(Long id) {
        cartRepository.findById(id).ifPresent(cartRepository::delete);
        logger.info("Deleted CartItem {}", id);
    }
}
