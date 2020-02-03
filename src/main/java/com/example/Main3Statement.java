package com.example;

import java.sql.*;

import static com.example.Configuration.*;

public class Main3Statement {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        boolean execute1 = statement.execute("SELECT * FROM animal");
        System.out.println(execute1);                                                                   // execute1 = true (zawsze zwraca true jeśli zapytanie jest typu SELECT)

        boolean execute2 = statement.execute("UPDATE animal SET name='Jasio' WHERE id=2");
        System.out.println(execute2);                                                                   // execute2 = false (zawsze zwraca false jeśli zapytanie jest typu INSERT, UPDATE, DELETE)

        int amount = statement.executeUpdate("DELETE FROM animal WHERE name='Rex'");                // executeUpdate używamy dla INSERT, UPDATE, DELETE
        System.out.println(amount);                                                                     // amount = 0 (zwraca ilość zmodyfikowanych wierszy)

        ResultSet resultSet = statement.executeQuery("SELECT * FROM animal");                       // executeQuery używamy dla SELECT
        while (resultSet.next()) {                                                                      // resultSet.next() zwraca true jeśli pobierze informacje o kolejnym wierszu lub zwraca false jesli nie ma więcej wierszy w odpowiedzi
            String name = resultSet.getString("name");                                      // możemy stosować: getString(indexKolumnyWBazie) lub getString(nazwaKolumnyWBazie)
            System.out.println(name);                                                                   // name = Reksio dla 1 iteracji, Jasio dla 2 i Benio dla 3
        }

        statement.close();
        connection.close();
    }
}