package repository;

import model.Author;
import model.Book;
import repository.interfacegenerique.CrudOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<Book> saveAll(List<Book> toSave) {
        bookList.addAll(toSave);
        return bookList;
    }
    @Override
    public Book save(Book toSave) {
        bookList.add(toSave);
        return toSave;
    }
    @Override
    public Book delete(Book toDelete) {
        bookList.remove(toDelete);
        return toDelete;
    }
}
