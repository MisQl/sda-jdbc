package com.example;

import java.sql.*;

import static com.example.Configuration.*;

public class Main7Injection {

    /*
        1.  Metoda login() jest podatna na atak SQL injection, gdyż używa konkatenacji stringów
        2.  Metoda securedLogin() jest bezpieczna, gdyż używa prepared statement

        W SQL injection do naszej kwerendy zamiast zmiennej password jest wklejany napis:   ' OR '1'='1
        To powoduje, że nasza kwerenda wygląda jak w przykładzie poniżej (linia 23)
    */

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        System.out.println(login("Ala", "password2", connection));              // true     SELECT * FROM user WHERE username='Ala' AND password='password2'
        System.out.println(login("Ala", "wrongPassword", connection));          // false

        System.out.println(login("Ala", "' OR '1'='1", connection));            // true     SELECT * FROM user WHERE username='Ala' AND password='' OR '1'='1'
        System.out.println(securedLogin("Ala", "' OR '1'='1", connection));     // false

        connection.close();
    }

    private static boolean login(String username, String password, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE username='" + username + "' AND password='" + password + "'");

        boolean isLogged = resultSet.next();
        statement.close();

        return isLogged;
    }

    private static boolean securedLogin(String username, String password, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        boolean isLogged = resultSet.next();
        preparedStatement.close();

        return isLogged;
    }
}

