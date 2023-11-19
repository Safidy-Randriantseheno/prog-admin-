package com.prog4.libraryManagement.repository;

import com.prog4.libraryManagement.repository.interfacegenerique.CrudOperations;
import lombok.AllArgsConstructor;
import com.prog4.libraryManagement.model.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Repository
public class AuthorCrudOperation implements CrudOperations<Author> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM author";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String lastName = resultSet.getString("last_name");
                String firstName = resultSet.getString("first_name");
                Author.Sex sex = Author.Sex.valueOf(resultSet.getString("sex"));
                authors.add(new Author(id, lastName,firstName, sex));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public List<Author> saveAll(List<Author> toSave) {
        List<Author> savedAuthors = new ArrayList<>();
        for (Author book : toSave) {
            Author savedAuthor = save(book);
            if (savedAuthor != null) {
                savedAuthors.add(savedAuthor);
            }
        }
        return savedAuthors;
    }

    @Override
    public Author save(Author toSave) {
        String query = "INSERT INTO author (id,last_name,first_name,sex) VALUES (?, ?, ?, ?)";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, toSave.getId());
            statement.setString(2, toSave.getFirstName());
            statement.setString(3, toSave.getLastName());
            statement.setString(4, toSave.getSex().toString());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    toSave.setId(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Author delete(Author toDelete) {
        String query = "DELETE FROM author WHERE id = ?";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, toDelete.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDelete;
    }

    public Author findAuthorById(String authorId) {
        String query = "SELECT * FROM author WHERE id = ?";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToAuthor(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Author mapResultSetToAuthor(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getString("id"));
        author.setLastName(resultSet.getString("last_name"));
        author.setFirstName(resultSet.getString("first_name"));
        author.setSex(Author.Sex.valueOf(resultSet.getString("sex")));
        return author;
    }
}
