package com.example;

import java.sql.*;

import static com.example.Configuration.*;

public class Main9Transaction {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        connection.setAutoCommit(false);                                                        // domyślnie tryb AutoCommit jest włączony, tutaj wymuszamy jego wyłączenie

        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE user SET username='Ambroży' WHERE id=1");           // ta zmiana się wykona, gdyż za nią jest ustawiony savepoint
        Savepoint savepoint = connection.setSavepoint();
        statement.executeUpdate("UPDATE user SET username='Eustachy' WHERE id=2");          // ta zmiana się nie wykona, gdyż został wywołany rollback()
        connection.rollback(savepoint);
        statement.executeUpdate("UPDATE user SET username='Bożydar' WHERE id=3");           // ta zmiana się wykona
        statement.close();

        connection.commit();                                                                    // commit() zatwierdza zmiany
        connection.close();
    }
}
