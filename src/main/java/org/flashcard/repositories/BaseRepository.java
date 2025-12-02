package org.flashcard.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseRepository {

    protected static final String URL = "jdbc:postgresql://localhost:5432/flashcarddb";
    protected static final String USER = "postgres";
    protected static final String PASSWORD = "123";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
