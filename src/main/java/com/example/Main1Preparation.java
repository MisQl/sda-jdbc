package com.example;

import java.sql.*;

import static com.example.Configuration.*;

public class Main1Preparation {

    /*
        1.  Przygotowanie bazy danych:

            CREATE SCHEMA jdbc_schema;
            CREATE TABLE animal(id BIGINT NOT NULL, name VARCHAR(50), age INT, PRIMARY KEY (id));
            INSERT INTO animal VALUES (1, 'Reksio', 5), (2, 'Mruczek', 4), (3, 'Benio', 10);

        2.  Parametry konfiguracyjne URL, USER, PASSWORD znajdują sie w com.example.Configuration.java
            Przykładowo:
            DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_schema?serverTimezone=Europe/Warsaw", "root", "michal1");
    */

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM animal");
        while (resultSet.next()) {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            int age = resultSet.getInt(3);
            System.out.println(String.format("Id: %s Name: %s, Age: %s", id, name, age));
        }

        statement.close();
        connection.close();
    }
}