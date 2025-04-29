package com.VY.bookstore.config;

import com.VY.bookstore.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * SecurityConfig – клас, що налаштовує параметри безпеки застосунку.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Сервіс, який відповідає за завантаження користувача з БД.
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    /**
     * Бін кодера паролів (BCrypt) – шифрує паролі перед зберіганням.
     *
     * @return PasswordEncoder instance.
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Ланцюжок фільтрів безпеки.
     * Тут ми:
     *  1. Вимикаємо CSRF (бо застосунок не використовує форми з токенами CSRF).
     *  2. Описуємо, які URL доступні тільки авторизованим користувачам.
     *  3. Налаштовуємо форму логіну.
     *  4. Налаштовуємо логаут.
     *
     * @param http HttpSecurity instance.
     * @return SecurityFilterChain instance.
     * @throws Exception if an error occurs.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF
                .csrf(csrf -> csrf.disable()) // Вимикаємо CSRF‑захист.

                // 2. Авторизація запитів
                .authorizeHttpRequests(auth -> auth
                        // Шлях "/order" доступний лише авторизованим користувачам.
                        .requestMatchers("/order").authenticated()
                        // Додавання товару до кошика також потребує авторизації.
                        .requestMatchers("/addToCart/{id}").authenticated()
                        // Перегляд кошика – тільки для залогінених.
                        .requestMatchers("/cart").authenticated()
                        // Усі інші запити доступні без авторизації.
                        .requestMatchers("/**").permitAll()
                )

                // 3. Форма логіну
                .formLogin(formlogin -> formlogin
                        // Сторінка з формою логіну.
                        .loginPage("/login")
                        // URL, на який надсилаються дані форми.
                        .loginProcessingUrl("/login")
                        // Куди перенаправляти після успішного входу.
                        .defaultSuccessUrl("/", true)
                        // Дозволяємо всім бачити сторінку логіну.
                        .permitAll()
                )

                // 4. Логаут
                .logout(logout -> logout
                        // Після виходу сесія буде знищена.
                        .invalidateHttpSession(true)
                        // Очищаємо контекст автентифікації.
                        .clearAuthentication(true)
                        // URL, який викликає логаут.
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        // Куди перенаправити після виходу.
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        // Повертаємо побудований ланцюжок фільтрів.
        return http.build();
    }

    /**
     * Налаштування менеджера автентифікації.
     * Вказуємо власний UserDetailsService та кодер паролів.
     *
     * @param auth AuthenticationManagerBuilder instance.
     * @throws Exception if an error occurs.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // Підтягуємо користувача з БД.
                .userDetailsService(customUserDetailsService)
                // Перевіряємо пароль через BCrypt.
                .passwordEncoder(passwordEncoder());
    }
}
