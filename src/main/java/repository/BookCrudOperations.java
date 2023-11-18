package repository;

import model.Author;
import model.Book;
import repository.interfacegenerique.CrudOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookCrudOperations implements CrudOperations<Book> {
    private Connection connection;
    public BookCrudOperations(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try {
            String query = "SELECT * FROM books";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book();
                    book.setId(resultSet.getString("id"));
                    book.setBookName(resultSet.getString("title"));
                    book.setPageNumbre(resultSet.getInt("pageNumbre"));
                    book.setReleaseDate(resultSet.getDate("releaseDate"));
                    book.setAvalability(Book.Availability.valueOf(resultSet.getString("avalability")));
                    book.setTopic(Book.Topic.valueOf(resultSet.getString("topic")));
                    String authorId = resultSet.getString("author_id");
                    Author author = findAuthorById(authorId);
                    book.setAuthor(author);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private Author findAuthorById(String authorId) {
        String query = "SELECT * FROM \"Author\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Author author = new Author();
                    author.setId(resultSet.getString("id"));
                    author.setName(resultSet.getString("name"));
                    return author;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public Book save(Book toSave) {
        String query = "INSERT INTO books (id, title, pageNumbre, releaseDate, avalability, topic, author_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, toSave.getId());
                statement.setString(2, toSave.getBookName());
                statement.setInt(3, toSave.getPageNumbre());
                statement.setDate(4, new java.sql.Date(toSave.getReleaseDate().getTime()));
                statement.setString(5, toSave.getAvalability().name());
                statement.setString(6, toSave.getTopic().name());
                statement.setString(7, toSave.getAuthor().getId());
                statement.addBatch();


            int[] affectedRows = statement.executeBatch();
            return toSave;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Book delete(Book toDelete) {
        bookList.remove(toDelete);
        return toDelete;
    }
}
