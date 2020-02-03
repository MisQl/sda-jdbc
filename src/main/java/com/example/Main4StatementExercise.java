package com.example;

import java.sql.*;

import static com.example.Configuration.*;

public class Main4StatementExercise {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS user(id BIGINT NOT NULL, username VARCHAR(50), password VARCHAR(50), PRIMARY KEY(id))");
        int updatedQuantity = statement.executeUpdate("INSERT INTO user VALUES (1, 'Jan', 'password1'), (2, 'Ala', 'password2'), (3, 'Mikołaj', 'passowrd3'), (4, 'Jan', 'password4'), (5, 'Kasia', 'password5'), (6, 'Ola', 'password6')");
        System.out.println(updatedQuantity);

        ResultSet resultSet = statement.executeQuery("SELECT username FROM user");
        while (resultSet.next()) {
            String name = resultSet.getString("username");
            System.out.println(name);
        }

        statement.executeUpdate("DELETE FROM user");

        statement.close();
        connection.close();
    }
}
