package com.example.dao;

import com.example.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.Configuration.*;

public class TaskDAO implements AutoCloseable {

    private Connection connection = null;

    public TaskDAO() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS task(id BIGINT NOT NULL, description VARCHAR(255), user_id BIGINT, PRIMARY KEY (id), CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES user(id))");
            statement.executeUpdate("DELETE from task");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Task task) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO task VALUE (?, ?, ?)");
        preparedStatement.setLong(1, task.getId());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setLong(3, task.getUserId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public Optional<Task> read(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM task WHERE id=?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            long taskId = resultSet.getLong("id");
            String description = resultSet.getString("description");
            long userId = resultSet.getLong("user_id");
            preparedStatement.close();
            return Optional.of(new Task(taskId, description, userId));
        }

        preparedStatement.close();
        return Optional.empty();
    }

    public List<Task> readAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM task");
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            long taskId = resultSet.getLong("id");
            String description = resultSet.getString("description");
            long userId = resultSet.getLong("user_id");
            tasks.add(new Task(taskId, description, userId));
        }

        statement.close();
        return tasks;
    }

    public void update(Task task) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE task SET description=?, user_id=? WHERE id=?");
        preparedStatement.setString(1, task.getDescription());
        preparedStatement.setLong(2, task.getUserId());
        preparedStatement.setLong(3, task.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void delete(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM task WHERE id=?");
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public List<Task> readAllForUser(String username) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM task JOIN user ON user.id=task.user_id WHERE user.username=?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            long taskId = resultSet.getLong("id");
            String description = resultSet.getString("description");
            long userId = resultSet.getLong("user_id");
            tasks.add(new Task(taskId, description, userId));
        }

        preparedStatement.close();
        return tasks;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
