package com.VY.bookstore.controller;

import com.VY.bookstore.model.CartItem;
import com.VY.bookstore.model.Orders;
import com.VY.bookstore.model.Product;
import com.VY.bookstore.model.User;
import com.VY.bookstore.service.CartService;
import com.VY.bookstore.service.OrderService;
import com.VY.bookstore.service.ProductService;
import com.VY.bookstore.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ProductController – відповідає за усі дії, повʼязані з товарами (книгами),
 * кошиком та замовленнями.
 */
@Controller
public class ProductController {

    /**
     * Логер для фіксації подій та спрощення дебагу.
     */
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    // ===== Інʼєкція сервісів =====

    /** Сервіс для роботи з товарами. */
    @Autowired
    private ProductService productService;

    /** Сервіс для роботи з кошиком. */
    @Autowired
    private CartService cartService;

    /** Сервіс для роботи з користувачами. */
    @Autowired
    private UserService userService;

    /** Сервіс для роботи з замовленнями. */
    @Autowired
    private OrderService orderService;

    // ===== Допоміжні методи =====

    /**
     * Повертає обʼєкт User на основі Principal (імені користувача).
     * @param principal поточний автентифікований користувач
     * @return User – модель користувача з БД
     */
    private User getUserDetails(Principal principal) {
        String username = principal.getName();
        return userService.findByUsername(username);
    }

    // ===== Глобальний @ModelAttribute =====

    /**
     * Додає у модель атрибут "log" зі значенням "Login" або "Logout" –
     * використовується у навігації.
     */
    @ModelAttribute
    public void addAttribute(Principal principal, Model model) {
        model.addAttribute("log", principal != null ? "Logout" : "Login");
    }

    // ===== Каталог товарів =====

    /**
     * Виводить усі товари.
     * @return сторінка "products.html"
     */
    @GetMapping("/products")
    public String listProducts(Model model) {
        try {
            List<Product> products = productService.getAllProducts();
            logger.info("Products retrieved: {}", products.size());
            model.addAttribute("products", products);
            return "products";
        } catch (Exception e) {
            logger.error("Error retrieving products", e);
            model.addAttribute("errorMessage", "Error retrieving products");
            return "error";
        }
    }

    // ===== Детальна сторінка товару =====

    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                model.addAttribute("errorMessage", "Product not found");
                return "error";
            }
            logger.info("Product retrieved: {}", product.getTitle());
            model.addAttribute("product", product);
            return "product";
        } catch (Exception e) {
            logger.error("Error retrieving product details", e);
            model.addAttribute("errorMessage", "Error retrieving product details");
            return "error";
        }
    }

    // ===== Новинки =====

    @GetMapping("/new-arrivals")
    public String viewNewArrivalsProducts(Model model) {
        try {
            List<Product> products = productService.getNewArrivalsProducts();
            if (products == null || products.isEmpty()) {
                model.addAttribute("errorMessage", "No new arrivals products found");
                return "error";
            }
            logger.info("New arrivals products retrieved: {} products", products.size());
            model.addAttribute("products", products);
            return "newArrivals";
        } catch (Exception e) {
            logger.error("Error retrieving new arrivals products", e);
            model.addAttribute("errorMessage", "Error retrieving new arrivals products");
            return "error";
        }
    }

    // ===== Бестселери =====

    @GetMapping("/best-sellers")
    public String viewBestsellerProducts(Model model) {
        try {
            List<Product> products = productService.getBestsellerProducts();
            if (products == null || products.isEmpty()) {
                model.addAttribute("errorMessage", "No bestseller products found");
                return "error";
            }
            logger.info("Bestseller products retrieved: {} products", products.size());
            model.addAttribute("products", products);
            return "bestSellers";
        } catch (Exception e) {
            logger.error("Error retrieving bestseller products", e);
            model.addAttribute("errorMessage", "Error retrieving bestseller products");
            return "error";
        }
    }

    // ===== Пошук книг =====

    @GetMapping("/search")
    public String searchBooks(@RequestParam(name = "title") String title, Model model) {
        List<Product> searchResults = productService.searchBooks(title, title);
        logger.info("Search results: {} items for query '{}'", searchResults.size(), title);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("searchtitle", title);
        return "searchResults";
    }

    // ===== Перегляд кошика =====

    @GetMapping("/cart")
    public String viewCarts(Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            return "redirect:/login";
        }

        User user = getUserDetails(principal);
        logger.info("Viewing cart for user {}", user.getUsername());

        // Фільтруємо позиції, що ще не увійшли до замовлення
        List<CartItem> cart = cartService.getCartbyUser(user.getId());
        List<CartItem> activeCart = new ArrayList<>();
        double total = 0.0;
        for (CartItem item : cart) {
            if (item.getOrder() == null) {
                activeCart.add(item);
                total += item.getPrice();
            }
        }
        model.addAttribute("cart", activeCart);
        model.addAttribute("total", total);
        return "User/cart";
    }

    // ===== Додавання товару до кошика =====

    @RequestMapping("/addToCart/{id}")
    public String addtoCart(@PathVariable Long id, Principal p) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            return "redirect:/login";
        }

        Product product = productService.getProductById(id);
        User user = getUserDetails(p);
        logger.info("Adding product {} to cart for user {}", product.getId(), user.getUsername());
        cartService.addtoCart(id, user.getId(), null);
        return "redirect:/cart";
    }

    // ===== Оновлення кількості =====

    @RequestMapping("/updateQuantity/{id}/{action}")
    public String updateQuantity(@PathVariable Long id, @PathVariable String action) {
        logger.info("Updating quantity of cart item {} with action {}", id, action);
        cartService.updateQuantity(id, action);
        return "redirect:/cart";
    }

    // ===== Видалення з кошика =====

    @RequestMapping("/deleteCart/{id}")
    public String deleteCart(@PathVariable Long id) {
        logger.info("Deleting cart item {}", id);
        cartService.deleteCart(id);
        return "redirect:/cart";
    }

    // ===== Створення замовлення =====

    @RequestMapping("/createOrder")
    public String userOrder(Model model, Principal principal) {
        logger.info("Creating order...");
        User user = getUserDetails(principal);

        // Отримуємо активні позиції кошика
        List<CartItem> cart = cartService.getCartbyUser(user.getId());
        List<CartItem> activeCart = new ArrayList<>();
        for (CartItem item : cart) {
            if (item.getOrder() == null) {
                activeCart.add(item);
            }
        }

        Orders orders = orderService.createOrder(activeCart, user);
        model.addAttribute("orders", orders);
        return "orderSummary";
    }

    // ===== Перегляд замовлень користувача =====

    @RequestMapping("/order")
    public String viewOrder(Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            return "redirect:/login";
        }

        logger.info("Viewing orders for user {}");
        User user = getUserDetails(principal);
        List<Orders> orders = orderService.viewOrders(user);
        model.addAttribute("orders", orders);
        return "orderSummary";
    }
}
