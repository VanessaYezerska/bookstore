package com.VY.bookstore;

import com.VY.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@SpringBootTest(
		properties = {
				"management.endpoint.health.show-details=always",
				"spring.datasource.url=jdbc:tc:mysql:9.2.0:///bookstore"
		},
		webEnvironment = RANDOM_PORT
)
class BookstoreApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void deleteAll() {
		userRepository.deleteAll();
	}

//	@Test
//	void checkUserIsExist() {
//		// given
//		var user = userRepository.saveAndFlush(
//				new User("yuji", "53cret", "Itadori Yuji", "yuji@yopmail.com", "Tokyo", "1234-5678-9012-3456")
//		);
//
//		// when
//		var userOptional = userRepository.findByUsername("yuji");
//
//		// then
//		assertThat(userOptional).isNotNull();
//		assertThat(userOptional.getUsername()).isEqualTo(user.getId());
//	}

}
