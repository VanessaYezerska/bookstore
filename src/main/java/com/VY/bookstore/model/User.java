package com.VY.bookstore.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * User – сутність, що представляє зареєстрованого користувача книжкового магазину.
 * <p>
 * Анотації Lombok {@code @Getter}, {@code @Setter}, {@code @NoArgsConstructor},
 * {@code @AllArgsConstructor} автоматично генерують геттери/сеттери та конструктори.
 * {@code @Entity} повідомляє JPA, що клас відображається на таблицю БД.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    // =================== Первинний ключ ===================

    /**
     * Унікальний ідентифікатор користувача.
     * Генерується БД автоматично (AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =================== Облікові дані ===================

    /** Логін (username), який використовується для входу. */
    private String username;

    /** Хеш пароля користувача. */
    private String password;

    // =================== Персональна інформація ===================

    /** Повне імʼя користувача (для відображення у UI). */
    private String fullname;

    /** Email – використовується для контактів і, за потреби, відновлення пароля. */
    private String email;

    /** Фізична адреса користувача (для доставки). */
    private String address;

    /** Платіжні реквізити (наприклад, останні цифри картки). *
     *  У реальному застосунку краще зберігати токенізовані дані або в окремому сервісі.
     */
    private String cardDetails;

    // =================== Користувацькі конструктори ===================

    /**
     * Додатковий конструктор для створення користувача з вибраними полями.
     */
    public User(String username,
                String password,
                String fullname,
                String email,
                String address,
                String cardDetails) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.address = address;
        this.cardDetails = cardDetails;
    }

    // =================== toString ===================

    /**
     * Перевизначений метод toString – корисно для логування.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", cardDetails='" + cardDetails + '\'' +
                '}';
    }
}
