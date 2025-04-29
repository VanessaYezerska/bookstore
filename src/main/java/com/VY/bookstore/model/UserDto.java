package com.VY.bookstore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * UserDto – Data Transfer Object для реєстрації / логіну користувача.
 * <p>
 * Використовується, щоб безпечно передавати дані з форми на бекенд,
 * не оголюючи сутність {@link User} напряму.
 * <p>
 * Анотації Lombok генерують стандартні методи:
 *  – {@code @Getter} / {@code @Setter} – геттери та сеттери для всіх полів;
 *  – {@code @NoArgsConstructor} – конструктор без аргументів;
 *  – {@code @AllArgsConstructor} – конструктор з усіма аргументами.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    // =================== Поля форми ===================

    /** Логін користувача. */
    private String username;

    /** Пароль (у сирому вигляді – буде захешовано перед збереженням). */
    private String password;

    /** Повне імʼя (для відображення у профілі). */
    private String fullname;

    /** Email користувача. */
    private String email;

    /** Адреса доставки. */
    private String address;

    /** Платіжні дані (наприклад, токен картки). */
    private String cardDetails;

    // =================== toString ===================

    /**
     * Перевизначений toString – корисний для логування (не друкуємо пароль у прод!).
     */
    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", cardDetails='" + cardDetails + '\'' +
                '}';
    }
}
