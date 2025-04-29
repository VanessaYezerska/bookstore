package com.VY.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Bookstore Application.
 * This class serves as the entry point for the Spring Boot application.
 */
@SpringBootApplication // This annotation denotes a Spring Boot application.
public class BookstoreApplication {

	/**
	 * The main method which serves as the entry point for the Java application.
	 *
	 * @param args Command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		// SpringApplication.run() method starts the entire Spring framework.
		SpringApplication.run(BookstoreApplication.class, args);
	}

}
