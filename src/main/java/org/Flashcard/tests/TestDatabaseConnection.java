package org.Flashcard.tests;

import org.Flashcard.repositories.BaseRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDatabaseConnection extends BaseRepository { // subclass

    public static void main(String[] args) {
        TestDatabaseConnection test = new TestDatabaseConnection();

        try (Connection conn = test.getConnection()) { // now accessible
            if (conn != null && !conn.isClosed()) {
                System.out.println("Successfully connected to the database!");
            } else {
                System.out.println("Connection failed!");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
