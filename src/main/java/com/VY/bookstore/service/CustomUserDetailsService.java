package com.VY.bookstore.service;

import java.util.Arrays;
import java.util.Collection;

import com.VY.bookstore.model.CustomUserDetails;
import com.VY.bookstore.model.User;
import com.VY.bookstore.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService – реалізація {@link UserDetailsService}, яка
 * підтягує дані користувача з БД і перетворює їх на об'єкт {@link UserDetails}
 * для Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /** Репозиторій користувачів. */
    private final UserRepository userRepository;

    /**
     * Конструктор з інʼєкцією залежності.
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // =================== Завантаження користувача ===================

    /**
     * Шукає користувача за username.
     * Якщо не знайдено – кидає {@link UsernameNotFoundException}, що призведе до
     * помилки автентифікації.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Отримуємо користувача з БД
        User user = userRepository.findByUsername(username);

        // 2. Перевірка на існування
        if (user == null) {
            throw new UsernameNotFoundException("Username or Password not found");
        }

        // 3. Повертаємо власний UserDetails
        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                authorities(),
                user.getFullname()
        );
    }

    // =================== Ролі / привілеї ===================

    /**
     * Формує набір GrantedAuthority для користувача.
     * У нашому випадку всі користувачі мають одну роль "USER".
     */
    private Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }
}
