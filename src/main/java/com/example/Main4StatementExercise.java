package com.example;

import java.sql.*;

import static com.example.Configuration.*;

public class Main4StatementExercise {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        Statement initialStatement = connection.createStatement();
        initialStatement.executeUpdate("CREATE TABLE IF NOT EXISTS user(" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "username VARCHAR(50) UNIQUE, " +
                "password VARCHAR(50), " +
                "PRIMARY KEY(id))");
        initialStatement.close();

        Statement updateStatement = connection.createStatement();
        int updatedQuantity = updateStatement.executeUpdate("INSERT INTO user(username, password) VALUES ('Jan', 'password1'), ('Ala', 'password2'), ('Mikołaj', 'passowrd3'), ('Kasia', 'password4'), ('Ola', 'password5')");
        System.out.println(updatedQuantity);
        updateStatement.close();

        Statement readStatement = connection.createStatement();
        ResultSet resultSet = initialStatement.executeQuery("SELECT username FROM user");
        while (resultSet.next()) {
            String name = resultSet.getString("username");
            System.out.println(name);
        }
        readStatement.close();

        Statement deleteStatement = connection.createStatement();
        deleteStatement.executeUpdate("DELETE FROM user");
        deleteStatement.close();

        initialStatement.close();
        connection.close();
    }
}
