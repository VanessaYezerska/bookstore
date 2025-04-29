package com.VY.bookstore.model;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * CustomUserDetails – власна реалізація інтерфейсу {@link UserDetails} з Spring Security.
 * Інкапсулює інформацію про користувача, яку фреймворк використовує
 * для автентифікації та авторизації.
 */
public class CustomUserDetails implements UserDetails {

    // =================== Поля моделі ===================

    /**
     * Логін користувача.
     */
    private String username;

    /**
     * Захешований пароль користувача.
     */
    private String password;

    /**
     * Колекція ролей/прав доступу, наданих користувачу.
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Повне ім'я користувача (для відображення у UI).
     */
    private String fullname;

    // =================== Конструктор ===================

    /**
     * Ініціалізує усі поля користувача.
     *
     * @param username    ім'я користувача
     * @param password    пароль (у вигляді хешу)
     * @param authorities список прав доступу
     * @param fullname    повне ім'я користувача
     */
    public CustomUserDetails(String username,
                             String password,
                             Collection<? extends GrantedAuthority> authorities,
                             String fullname) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.fullname = fullname;
    }

    // =================== Геттери ===================

    /**
     * Повертає повне ім'я користувача.
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Повертає список GrantedAuthority (ролі/привілеї).
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Повертає пароль користувача.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Повертає ім'я користувача.
     */
    @Override
    public String getUsername() {
        return username;
    }

    // =================== Стани акаунта ===================

    /**
     * Чи не минув термін дії акаунта.
     * У нашому застосунку ця перевірка не використовується – повертаємо true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Чи не заблокований акаунт користувача.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Чи не минув термін дії пароля.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Чи активований користувач.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
