package com.example;

import java.sql.*;

import static com.example.Configuration.*;

public class Main2TryWithResources {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM animal");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                int age = resultSet.getInt(3);
                System.out.println(String.format("Id: %s Name: %s, Age: %s", id, name, age));
            }
            // po wyjściu z ciała bloku try wywoływane jest w kolejności: statement.close() i następnie connection.close()
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}