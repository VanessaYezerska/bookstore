package com.VY.bookstore.controller;

import com.VY.bookstore.model.User;
import com.VY.bookstore.model.UserDto;
import com.VY.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

/**
 * UserController – контролер, що відповідає за дії, повʼязані з користувачем:
 * реєстрацію, логін та виведення домашньої сторінки.
 */
@Controller
public class UserController {

    /**
     * Сервіс Spring Security для завантаження деталей користувача за імʼям.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Бізнес‑логіка, що працює з користувачами (створення, пошук тощо).
     */
    @Autowired
    private UserService userService;

    /**
     * Конструктор (необовʼязковий при @Autowired, але залишаємо для явності).
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // =================== Home ===================

    /**
     * Домашня сторінка після логіну.
     * 1. Отримуємо деталі користувача через UserDetailsService.
     * 2. Передаємо їх у модель, щоб View могла відобразити (наприклад, імʼя).
     */
    @GetMapping("/index")
    public String home(Model model, Principal principal) {
        // principal ніколи не буде null, бо сторінка доступна лише після логіну.
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        return "index";
    }

    // =================== Login ===================

    /**
     * Сторінка логіну.
     * Просто додаємо порожній DTO, щоб заповнити форму.
     */
    @GetMapping("/login")
    public String login(Model model, UserDto userDto, Principal principal) {
        model.addAttribute("user", userDto);
        return "login";
    }

    // =================== Register (GET) ===================

    /**
     * Сторінка реєстрації.
     */
    @GetMapping("/register")
    public String register(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "register";
    }

    // =================== Register (POST) ===================

    /**
     * Обробка форми реєстрації.
     * 1. Перевіряємо, чи існує користувач з таким username.
     * 2. Якщо існує – повертаємо форму з повідомленням.
     * 3. Якщо ні – зберігаємо нового користувача і робимо redirect з параметром success.
     */
    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") UserDto userDto, Model model) {
        User existingUser = userService.findByUsername(userDto.getUsername());
        if (existingUser != null) {
            model.addAttribute("Userexist", existingUser);
            return "register";
        }
        userService.save(userDto);
        return "redirect:/register?success";
    }
}
