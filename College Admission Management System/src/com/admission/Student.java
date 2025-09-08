package com.admission;

import java.sql.*;
import java.io.FileWriter;

public class Student {

    private final String URL = DBConnection.url();
    private final String USER = DBConnection.user();
    private final String PASSWORD = DBConnection.password();

    // Register student
    public void registerStudent(String name, int marks, String email) throws Exception {
        String sql = "INSERT INTO students (name, marks, email) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, marks);
            ps.setString(3, email);
            ps.executeUpdate();
            System.out.println("✅ Student registered.");
        }
    }

    // Add course
    public void addCourse(String courseName, int cutoff) throws Exception {
        String sql = "INSERT INTO courses (name, cutoff_marks) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, courseName);
            ps.setInt(2, cutoff);
            ps.executeUpdate();
            System.out.println("✅ Course added.");
        }
    }

    // Submit application
    public void submitApplication(int stdId, int courseId) throws Exception {
        String sql = "INSERT INTO applications (std_id, course_id) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stdId);
            ps.setInt(2, courseId);
            ps.executeUpdate();
            System.out.println("✅ Application submitted (PENDING).");
        }
    }

    // Process admissions
    public void processAdmissions() throws Exception {
        String sql = "UPDATE applications a " +
                     "JOIN students s ON a.std_id = s.std_id " +
                     "JOIN courses c ON a.course_id = c.course_id " +
                     "SET a.status = CASE WHEN s.marks >= c.cutoff_marks THEN 'APPROVED' ELSE 'REJECTED' END " +
                     "WHERE a.status <> 'APPROVED' OR a.status IS NULL";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            int rows = stmt.executeUpdate(sql);
            System.out.println("✅ Admissions processed. Updated: " + rows);
        }
    }

    // Export admission list
    public void exportAdmissionListCsv(String fileName) throws Exception {
        String sql = "SELECT s.std_id, s.name, s.marks, c.name AS course, c.cutoff_marks, a.status " +
                     "FROM applications a " +
                     "JOIN students s ON a.std_id = s.std_id " +
                     "JOIN courses c ON a.course_id = c.course_id " +
                     "ORDER BY c.name, s.marks DESC, s.name";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             FileWriter csv = new FileWriter(fileName)) {

            csv.append("StudentID,Student,Marks,Course,Cutoff,Status\n");
            while (rs.next()) {
                csv.append(rs.getInt("std_id") + ",")
                   .append(escape(rs.getString("name")) + ",")
                   .append(rs.getInt("marks") + ",")
                   .append(escape(rs.getString("course")) + ",")
                   .append(rs.getInt("cutoff_marks") + ",")
                   .append(rs.getString("status") + "\n");
            }
            csv.flush();
            System.out.println("✅ CSV exported: " + fileName);
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    // Extra: list students
    public void listStudents() throws Exception {
        String sql = "SELECT std_id, name, marks, email FROM students ORDER BY std_id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n-- Students --");
            while (rs.next()) {
                System.out.printf("%d | %s | %d | %s%n",
                        rs.getInt("std_id"), rs.getString("name"), rs.getInt("marks"), rs.getString("email"));
            }
        }
    }

    // Extra: list courses
    public void listCourses() throws Exception {
        String sql = "SELECT course_id, name, cutoff_marks FROM courses ORDER BY course_id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n-- Courses --");
            while (rs.next()) {
                System.out.printf("%d | %s | cutoff=%d%n",
                        rs.getInt("course_id"), rs.getString("name"), rs.getInt("cutoff_marks"));
            }
        }
    }

    // Extra: list applications
    public void listApplications() throws Exception {
        String sql = "SELECT a.application_id, s.name AS student, c.name AS course, a.status, a.applied_at " +
                     "FROM applications a " +
                     "JOIN students s ON a.std_id = s.std_id " +
                     "JOIN courses c ON a.course_id = c.course_id " +
                     "ORDER BY a.application_id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n-- Applications --");
            while (rs.next()) {
                System.out.printf("%d | %s -> %s | %s | %s%n",
                        rs.getInt("application_id"),
                        rs.getString("student"),
                        rs.getString("course"),
                        rs.getString("status"),
                        rs.getTimestamp("applied_at"));
            }
        }
    }
    // Get last inserted student ID
    public int getLastInsertedStudentId() throws Exception {
        String sql = "SELECT MAX(std_id) AS id FROM students";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt("id");
        }
        return -1;
    }

    // Get course ID by course name
    public int getCourseIdByName(String courseName) throws Exception {
        String sql = "SELECT course_id FROM courses WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, courseName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("course_id");
            }
        }
        return -1;
    }

}
