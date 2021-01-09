package com.example;

import java.sql.*;

import static com.example.Configuration.*;

public class Main3Statement {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        Statement updateStatement = connection.createStatement();
        int amount = updateStatement.executeUpdate("UPDATE animal SET name='Jasio' WHERE id=2");        // executeUpdate używamy dla INSERT, UPDATE, DELETE
        System.out.println(amount);                                                                     // amount = 1 (zwraca ilość zmodyfikowanych wierszy)
        updateStatement.close();

        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM animal");                     // executeQuery używamy dla SELECT
        while (resultSet.next()) {                                                                      // resultSet.next() zwraca true jeśli pobierze informacje o kolejnym wierszu lub zwraca false jesli nie ma więcej wierszy w odpowiedzi
            String name = resultSet.getString("name");                                                  // możemy stosować: getString(indexKolumnyWBazie) lub getString(nazwaKolumnyWBazie)
            System.out.println(name);                                                                   // name = Reksio dla 1 iteracji, Jasio dla 2 i Benio dla 3
        }
        selectStatement.close();

        Statement truncateStatement = connection.createStatement();
        boolean hasResult = truncateStatement.execute("TRUNCATE TABLE animal");                         // execute możemy stosować do wszystkich typów zapytań
        System.out.println(hasResult);                                                                  // jeśli zapytanie będzie typu SELECT zwróci true
        truncateStatement.close();                                                                      // dla TRUNCATE zwróci false

        connection.close();
    }
}
