package repository;

import model.Author;
import model.Book;
import net.bytebuddy.agent.builder.AgentBuilder;
import repository.interfacegenerique.CrudOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorCrudOperation implements CrudOperations<Author> {
    private Connection connection;
    public AuthorCrudOperation(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Author> findAll(){

        List<Author> authors = new ArrayList<>();
        try {
            String query = "SELECT * FROM authors";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Author author = new Author();
                    author.setId(resultSet.getString("id"));
                    author.setName(resultSet.getString("name"));
                    author.setSex(Author.Sex.valueOf(resultSet.getString("sex")));
                    authors.add(author);
                }
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
        String query = "INSERT INTO authors (id, name, sex) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, toSave.getId());
            statement.setString(2, toSave.getName());
            statement.setString(3, toSave.getSex().name());
            statement.addBatch();
            int[] affectedRows = statement.executeBatch();
            return toSave;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Author delete(Author toDelete) {
        String query = "DELETE FROM authors WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
}
