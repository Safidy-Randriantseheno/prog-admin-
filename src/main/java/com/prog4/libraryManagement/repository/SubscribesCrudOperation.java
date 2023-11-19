package com.prog4.libraryManagement.repository;

import com.prog4.libraryManagement.model.Author;
import com.prog4.libraryManagement.model.Book;
import com.prog4.libraryManagement.model.User;
import com.prog4.libraryManagement.repository.interfacegenerique.CrudOperations;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
@AllArgsConstructor
public class SubscribesCrudOperation implements CrudOperations<User> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()){
             String query = "SELECT * FROM book";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = mapResultSetToUser(resultSet);
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public List<User> saveAll(List<User> toSave) {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            for (User user : toSave) {
                save(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public User save(User toSave) {
            String query = "INSERT INTO users (id, firstName, lastName, email, ref, status, phone, birthDate, entranceDatetime, sex, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
                setParametersForUser(statement, toSave);
                statement.executeUpdate();
            }
         catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public User delete(User toDelete) {
            String query = "DELETE FROM users WHERE id = ?";
            try (Connection connection = jdbcTemplate.getDataSource().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, toDelete.getId());
                statement.executeUpdate();
            }
         catch (SQLException e) {
            e.printStackTrace();
        }
        return toDelete;
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setEmail(resultSet.getString("email"));
        user.setAddress(resultSet.getString("address"));
        user.setPhone(resultSet.getString("phone"));
        user.setSex(User.Sex.valueOf(resultSet.getString("sex")));
        user.setRole(User.Role.valueOf(resultSet.getString("Role")));
        user.setStatus(User.Status.valueOf(resultSet.getString("status")));
        user.setRef(resultSet.getString("ref"));
        user.setBirthDate(resultSet.getDate("birthDate").toLocalDate());
        user.setEntranceDatetime(resultSet.getTimestamp("entranceDatetime").toInstant());
        return user;
    }

    private void setParametersForUser(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getId());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getRef());
        statement.setString(6, user.getStatus().name());
        statement.setString(7, user.getPhone());
        statement.setDate(8, java.sql.Date.valueOf(user.getBirthDate()));
        statement.setTimestamp(9, java.sql.Timestamp.from(user.getEntranceDatetime()));
        statement.setString(10, user.getSex().name());
        statement.setString(11, user.getAddress());
    }
}
