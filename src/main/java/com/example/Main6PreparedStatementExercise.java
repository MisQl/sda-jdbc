package com.example;

import java.sql.*;
import java.util.List;

import static com.example.Configuration.*;

public class Main6PreparedStatementExercise {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        Statement deleteStatement = connection.createStatement();
        deleteStatement.execute("TRUNCATE TABLE user");
        deleteStatement.close();

        List<String> names = List.of("Jan", "Ala", "Mikołaj", "Alicja", "Kasia");
        List<String> passwords = List.of("password1", "password2", "password3", "password4", "password5", "password6");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user(username, password) VALUE(?, ?)");
        for (int i = 0; i < 5; i++) {
            preparedStatement.setString(1, names.get(i));           // 1 oznacza pierwszy znak zapytania w kwerendzie
            preparedStatement.setString(2, passwords.get(i));       // 2 oznacza drugi znak zapytania w kwerendzie
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT username FROM user");
        while (resultSet.next()) {
            String name = resultSet.getString("username");
            System.out.println(name);
        }

        statement.close();
        connection.close();
    }
}
