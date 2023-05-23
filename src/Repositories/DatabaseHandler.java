package Repositories;

import java.sql.*;

public class DatabaseHandler {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/pao_library_db";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = " ";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    // Poți adăuga și alte metode pentru a actualiza sau șterge cărți, dacă e nevoie
}
