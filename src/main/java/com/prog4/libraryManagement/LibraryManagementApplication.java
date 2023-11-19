package com.prog4.libraryManagement;

import com.prog4.libraryManagement.model.Author;
import com.prog4.libraryManagement.repository.AuthorCrudOperation;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;


@SpringBootApplication
public class LibraryManagementApplication {
	private static final Logger logger = LoggerFactory.getLogger(LibraryManagementApplication.class);
	@Value("${DB_URL}")
	private String jdbcUrl;

		@Value("${DB_USER}")
		private String user;

		@Value("${DB_PASSWORD}")
		private String password;

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(JdbcTemplate jdbcTemplate,AuthorCrudOperation authorCrudOperations) {
		return (args) -> {
			try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
				logger.info("Connexion à la base de données établie avec succès.");
			} catch (SQLException e) {
				logger.error("Erreur lors de la connexion à la base de données.", e);
			}

			testAuthorCrudOperations(authorCrudOperations);
		};
	}
	private static void testAuthorCrudOperations(AuthorCrudOperation authorCrudOperations) {
		// Test findAll
		List<Author> authors = authorCrudOperations.findAll();
		logger.info("Authors: {}", authors);

		// Test the save method
		Author newAuthor = Author.builder()
				.id("1")
				.lastName("Rakoto")
				.firstName("solo")
				.sex(Author.Sex.M)
				.build();
		Author savedAuthor = authorCrudOperations.save(newAuthor);
		logger.info("Author saved: {}", savedAuthor);

		// Test the saveAll method
		List<Author> authorListToSave = Arrays.asList(
				Author.builder()
						.id("2")
						.lastName("Rakoto")
						.firstName("solo")
						.sex(Author.Sex.M)
				.build(),
				Author.builder()
						.id("3")
						.lastName("Rakoto")
						.firstName("solo")
						.sex(Author.Sex.F)
						.build());
		List<Author> savedAuthors = authorCrudOperations.saveAll(authorListToSave);
		logger.info("Authors saved: {}", savedAuthors);

		// Test the delete method
		Author authorToDelete = savedAuthor;
		Author deletedAuthor = authorCrudOperations.delete(authorToDelete);
		logger.info("Author deleted: {}", deletedAuthor);
	}
}
