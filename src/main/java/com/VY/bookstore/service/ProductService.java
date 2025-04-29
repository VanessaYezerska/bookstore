package com.VY.bookstore.service;

import com.VY.bookstore.model.Product;
import com.VY.bookstore.repository.CartRepository;
import com.VY.bookstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductService – сервісна логіка, що працює з товарами (книгами).
 * <p>
 * Забезпечує взаємодію між контролерами та репозиторієм, інкапсулюючи бізнес‑правила
 * (наприклад, вибірка бестселерів чи новинок).
 */
@Service
public class ProductService {

    // =================== Репозиторії ===================

    /** Репозиторій для операцій із сутністю {@link Product}. */
    @Autowired
    private ProductRepository productRepository;

    /** Репозиторій для доступу до позицій кошика (поки не використовується тут, але може знадобитися). */
    @Autowired
    private CartRepository cartRepository;

    // =================== Отримання всіх товарів ===================

    /**
     * Повертає повний список книг у каталозі.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // =================== Отримання товару за ID ===================

    /**
     * Шукає книгу за її ідентифікатором.
     *
     * @param id ідентифікатор товару
     * @return {@link Product} або null, якщо не знайдено
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // =================== Бестселери ===================

    /**
     * Повертає всі книги, позначені як бестселери.
     */
    public List<Product> getBestsellerProducts() {
        return productRepository.findByIsBestsellerTrue();
    }

    // =================== Новинки ===================

    /**
     * Повертає всі книги, позначені як нові надходження.
     */
    public List<Product> getNewArrivalsProducts() {
        return productRepository.findByIsNewArrivalTrue();
    }

    // =================== Пошук книг ===================

    /**
     * Пошук книг за назвою або автором (частковий збіг, регістр не враховується).
     *
     * @param title  підрядок у назві
     * @param author підрядок в авторі
     * @return список книг, що відповідають критеріям
     */
    public List<Product> searchBooks(String title, String author) {
        return productRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(title, author);
    }
}
