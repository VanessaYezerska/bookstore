package com.VY.bookstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Product – сутність, що представляє книгу/товар у книжковому магазині.
 * <p>
 * Анотація {@code @Entity} повідомляє JPA, що клас потрібно зіставити з таблицею БД.
 * Анотація {@code @Data} від Lombok генерує геттери, сеттери, toString, equals та hashCode.
 */
@Entity
@Data
public class Product {

    // =================== Первинний ключ ===================

    /**
     * Унікальний ідентифікатор книги.
     * Генерується автоматично стратегією AUTO_INCREMENT.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =================== Основні метадані книги ===================

    /** Назва книги. */
    private String title;

    /** Автор книги. */
    private String author;

    /** Видавництво. */
    private String publisher;

    /**
     * Дата публікації (у Google Books API може приходити як рядок, але зберігаємо Date).
     */
    private Date publishedDate;

    /**
     * Опис книги (довгий текст), тому використовуємо columnDefinition = "TEXT".
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /** ISBN або інший унікальний ідентифікатор. */
    private String isbnIdentifier;

    /** Кількість сторінок. */
    private Integer pageCount;

    /** Категорії (жанри), наприклад: "Programming, Java". */
    private String categories;

    /** Мова видання (ISO‑код, наприклад "en", "uk"). */
    private String language;

    /**
     * URL або base64‑рядок зображення обкладинки.
     * Зберігаємо як String для простоти.
     */
    private String imageLinks;

    // =================== Ціни ===================

    /**
     * Списокова (рекомендована) ціна видавця.
     */
    @Column(name = "list_price")
    private Double listPrice;

    /**
     * Роздрібна (продажна) ціна у магазині.
     */
    @Column(name = "retail_price")
    private Double retailPrice;

    // =================== Прапорці для відбору ===================

    /** Чи є книга бестселером. */
    @Column(name = "is_bestseller")
    private Boolean isBestseller;

    /** Чи є книга новинкою. */
    @Column(name = "is_new_arrival")
    private Boolean isNewArrival;
}
