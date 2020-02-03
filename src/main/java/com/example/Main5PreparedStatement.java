package com.example;

import java.sql.*;

import static com.example.Configuration.*;

public class Main5PreparedStatement {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT age FROM animal WHERE name=?");
        preparedStatement.setString(1, "Benio");

        // preparedStatement.setString(indexZnakuZapytaniaWKwerendzie, wpisywanaWartość)
        // SELECT age FROM animal WHERE name='Benio'

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int age = resultSet.getInt("age");
            System.out.println(age);                                // age = 10
        }

        preparedStatement.close();
        connection.close();
    }
}