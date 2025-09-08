package com.admission;

import java.sql.*;

public class DBConnection {
    private static final String DB_NAME = "isaac_database";
    private static final String URL_WITHOUT_DB = "jdbc:mysql://localhost:3306?useSSL=false&serverTimezone=UTC";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public DBConnection() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL_WITHOUT_DB, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            System.out.println("Database ready: " + DB_NAME);
        }

        // Connect to DB to create/check tables
        try (Connection dbConn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createStudentsTableIfNotExists(dbConn);
            createCoursesTableIfNotExists(dbConn);
            createApplicationsTableIfNotExists(dbConn);
            ensureApplicationsHasStatusColumn(dbConn); // ✅ auto-fix column
        }
    }

    private static void createStudentsTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                " std_id INT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(100) NOT NULL," +
                " marks INT CHECK (marks >= 0 AND marks <= 100)," +
                " email VARCHAR(100)," +
                " UNIQUE KEY uniq_email (email)" +
                ") ENGINE=InnoDB;";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Table ready: students");
        }
    }

    private static void createCoursesTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS courses (" +
                " course_id INT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(100) NOT NULL," +
                " cutoff_marks INT CHECK (cutoff_marks >= 0 AND cutoff_marks <= 100)," +
                " UNIQUE KEY uniq_course_name (name)" +
                ") ENGINE=InnoDB;";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Table ready: courses");
        }
    }

    private static void createApplicationsTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS applications (" +
                " application_id INT PRIMARY KEY AUTO_INCREMENT," +
                " std_id INT NOT NULL," +
                " course_id INT NOT NULL," +
                " applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                " CONSTRAINT fk_app_std FOREIGN KEY (std_id) REFERENCES students(std_id) ON DELETE CASCADE," +
                " CONSTRAINT fk_app_course FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE," +
                " INDEX idx_app_std (std_id)," +
                " INDEX idx_app_course (course_id)" +
                ") ENGINE=InnoDB;";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Table ready: applications");
        }
    }

    // ✅ Ensure "status" column exists (if missing, add it)
    private static void ensureApplicationsHasStatusColumn(Connection conn) throws SQLException {
        boolean hasStatus = false;
        String checkSql = "SHOW COLUMNS FROM applications LIKE 'status'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {
            if (rs.next()) {
                hasStatus = true;
            }
        }
        if (!hasStatus) {
            String alter = "ALTER TABLE applications ADD COLUMN status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING'";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(alter);
                System.out.println("✅ Added missing column 'status' to applications table.");
            }
        }
    }

    public static String url() { return URL; }
    public static String user() { return USER; }
    public static String password() { return PASSWORD; }
}
