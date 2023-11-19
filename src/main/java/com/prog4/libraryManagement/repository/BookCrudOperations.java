package com.prog4.libraryManagement.repository;

import com.prog4.libraryManagement.repository.interfacegenerique.CrudOperations;
import lombok.AllArgsConstructor;
import com.prog4.libraryManagement.model.Author;
import com.prog4.libraryManagement.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
@AllArgsConstructor
public class BookCrudOperations implements CrudOperations<Book> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String query = "SELECT * FROM book";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = mapResultSetToBook(resultSet);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book save(Book toSave) {
        String query = "INSERT INTO book (id, book_name, page_number, release_date, availability, topic, author_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, toSave.getId());
            statement.setString(2, toSave.getBookName());
            statement.setInt(3, toSave.getPageNumber());
            statement.setDate(4, new java.sql.Date(toSave.getReleaseDate().getTime()));
            statement.setString(5, toSave.getAvailability().name());
            statement.setString(6, toSave.getTopic().name());
            statement.setString(7, toSave.getAuthor().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public List<Book> saveAll(List<Book> toSave) {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            for (Book book : toSave) {
                save(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    @Transactional
    public Book delete(Book toDelete) {
        String query = "DELETE FROM book WHERE id = ?";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, toDelete.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return toDelete;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getString("id"));
        book.setBookName(resultSet.getString("book_name"));
        book.setPageNumber(resultSet.getInt("page_number"));
        book.setReleaseDate(resultSet.getDate("release_date"));
        book.setAvailability(Book.Availability.valueOf(resultSet.getString("availability")));
        book.setTopic(Book.Topic.valueOf(resultSet.getString("topic")));
        String authorId = resultSet.getString("author_id");
        Author author = findAuthorById(authorId);
        book.setAuthor(author);
        return book;
    }

    private Author findAuthorById(String authorId) {
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
