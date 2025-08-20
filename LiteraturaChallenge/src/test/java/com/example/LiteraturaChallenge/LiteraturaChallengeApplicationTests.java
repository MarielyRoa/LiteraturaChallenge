package com.example.LiteraturaChallenge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class LiteraturaChallengeApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void testDatabaseConnection() throws Exception {
		try (Connection connection = dataSource.getConnection()) {
			System.out.println("✅ Conectado a la base de datos: " + connection.getMetaData().getURL());
			assertThat(connection.isValid(1)).isTrue();
		}
	}

}
