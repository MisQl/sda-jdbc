package com.example;

import java.sql.*;
import java.util.List;

import static com.example.Configuration.*;

public class Main6PreparedStatementExercise {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        Statement deleteStatement = connection.createStatement();
        deleteStatement.executeUpdate("DELETE FROM user");
        deleteStatement.close();

        List<String> names = List.of("Jan", "Ala", "Mikołaj", "Jan", "Kasia");
        List<String> passwords = List.of("password1", "password2", "password3", "password4", "password5", "password6");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user VALUE(?, ?, ?)");
        for (int i = 1; i <= 5; i++) {
            preparedStatement.setLong(1, i);                            // 1 oznacza pierwszy znak zapytania w kwerendzie
            preparedStatement.setString(2, names.get(i - 1));           // 2 oznacza drugi znak zapytania w kwerendzie
            preparedStatement.setString(3, passwords.get(i - 1));
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
