package com.VY.bookstore.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.VY.bookstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.VY.bookstore.model.Product;
import com.VY.bookstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HomeController – головний контролер, який обробляє запити на домашню сторінку.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    /**
     * Логер для зручного логування подій у контролері.
     */
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Сервіс роботи з користувачами (поки не використовується у цьому класі, але може
     * знадобитись для майбутніх розширень: перевірка ролей, історія замовлень тощо).
     */
    @Autowired
    private UserService userService;

    /**
     * Сервіс роботи з товарами – отримуємо списки новинок та бестселерів.
     */
    @Autowired
    private ProductService productService;

    /**
     * Додає атрибути до моделі перед кожним запитом. Тут ми визначаємо, який текст
     * відображати у кнопці/посиланню авторизації: «Login» або «Logout».
     * Якщо користувач автентифікований (principal != null) – показуємо «Logout».
     * Інакше – «Login».
     *
     * @param principal поточний автентифікований користувач (або null, якщо гість)
     * @param model     модель для передачі даних у View
     */
    @ModelAttribute
    public void addAttribute(Principal principal, Model model) {
        String login = "Login";
        String logout = "Logout";
        // Перевіряємо, чи є користувач залогіненим.
        if (principal != null) {
            model.addAttribute("log", logout);
        } else {
            model.addAttribute("log", login);
        }
    }

    /**
     * Обробляє GET‑запити за кореневою адресою «/».
     * Логіка:
     *  1. Отримуємо списки нових надходжень (newArrivals) та бестселерів (bestSellers).
     *  2. Перемішуємо списки, щоб кожне оновлення сторінки показувало випадкові товари.
     *  3. Відбираємо перші 4 елементи з кожного списку.
     *  4. Кладемо обмежені списки у модель, щоб View могла їх відобразити.
     *
     * @param model     модель для передачі даних у представлення (View)
     * @param principal автентифікований користувач (не використовується тут, але може знадобитись)
     * @return назва View, яке потрібно відрендерити ("index")
     */
    @GetMapping("/")
    public String listProducts(Model model, Principal principal) {
        // 1. Отримуємо списки товарів
        List<Product> newArrivals = productService.getNewArrivalsProducts();
        List<Product> bestSellers = productService.getBestsellerProducts();

        // 2. Перемішуємо списки, щоб урізноманітнити показ товарів
        Collections.shuffle(newArrivals);
        Collections.shuffle(bestSellers);

        // 3. Обмежуємо до 4 позицій
        List<Product> randomNewArrivals = newArrivals.stream()
                .limit(4)
                .collect(Collectors.toList());
        List<Product> randomBestSellers = bestSellers.stream()
                .limit(4)
                .collect(Collectors.toList());

        // 4. Кладемо у модель
        model.addAttribute("newArrivals", randomNewArrivals);
        model.addAttribute("bestSellers", randomBestSellers);

        logger.info("Listing 4 random products for new arrival & best sellers section");

        return "index"; // Повертаємо назву шаблону (наприклад, index.html)
    }
}
