package com.example;

import com.example.model.User;

import java.sql.*;
import java.util.ArrayList;

import static com.example.Configuration.*;

public class Main8UserExercise {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

        ArrayList<User> users = new ArrayList<>();                                      // jeśli nasz kursor znajduje się na napisie User możemy wcisnąć CTRL + B i przeniesie nas do definicji klasy
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            users.add(new User(id, username, password));
        }

        statement.close();
        connection.close();

        users.forEach(System.out::println);
    }
}

