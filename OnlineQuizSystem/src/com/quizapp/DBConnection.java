package com.quizapp;

import java.sql.*;

public class DBConnection {
    private static final String DB_NAME = "isaac_quizdb";
    private static final String URL_WITHOUT_DB = "jdbc:mysql://localhost:3306?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public DBConnection() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL_WITHOUT_DB, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME +
                               " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            System.out.println("✅ Database ready: " + DB_NAME);
        }
        try (Connection dbConn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createUsersTableIfNotExists(dbConn);
            createQuestionsTableIfNotExists(dbConn);
            createResultsTableIfNotExists(dbConn);
            seedQuestionsIfEmpty(dbConn);
        }
    }

    private static void createUsersTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                " user_id INT PRIMARY KEY AUTO_INCREMENT," +
                " username VARCHAR(100) NOT NULL UNIQUE," +
                " password VARCHAR(100) NOT NULL" +
                ") ENGINE=InnoDB;";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("✅ Table ready: users");
        }
    }

    private static void createQuestionsTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS questions (" +
                " question_id INT PRIMARY KEY AUTO_INCREMENT," +
                " question_text TEXT NOT NULL," +
                " option1 VARCHAR(255)," +
                " option2 VARCHAR(255)," +
                " option3 VARCHAR(255)," +
                " option4 VARCHAR(255)," +
                " correct_option VARCHAR(1) NOT NULL" +
                ") ENGINE=InnoDB;";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("✅ Table ready: questions");
        }
    }

    private static void createResultsTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS results (" +
                " result_id INT PRIMARY KEY AUTO_INCREMENT," +
                " user_id INT NOT NULL," +
                " score INT NOT NULL," +
                " status VARCHAR(10) NOT NULL," +
                " taken_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                " FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE" +
                ") ENGINE=InnoDB;";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("✅ Table ready: results");
        }
    }

    private static void seedQuestionsIfEmpty(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM questions";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next() && rs.getInt(1) == 0) {
                new QuestionSeeder();
                System.out.println("✅ Questions table seeded.");
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static String url() { return URL; }
    public static String user() { return USER; }
    public static String password() { return PASSWORD; }
}
