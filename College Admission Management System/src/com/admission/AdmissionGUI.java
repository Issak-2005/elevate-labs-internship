package com.admission;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdmissionGUI extends JFrame {
    private Student svc;

    // Fixed courses with cutoff marks
    private Map<String, Integer> courses = new LinkedHashMap<String, Integer>() {{
        put("BCA", 70);
        put("BSC", 60);
        put("B.COM", 60);
        put("BBA", 60);
        put("B.TECH", 70);
        put("MCA",75);
        put("MSC",70);
        put("M.COM",70);
        put("MBA",70);
        put("M.TECH", 75);
    }};

    public AdmissionGUI(Student svc) {
        this.svc = svc;

        // Big window
        setTitle("College Admission Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔹 Welcome Label
        JLabel welcomeLabel = new JLabel("WELCOME TO COLLEGE ADMISSION MANAGEMENT SYSTEM", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 26));
        welcomeLabel.setForeground(new Color(0, 51, 102));
        add(welcomeLabel, BorderLayout.NORTH);

        // 🔹 Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 20, 20)); // only 3 buttons now

        JButton btnStudent = new JButton("Register Student");
        JButton btnCourses = new JButton("Available Courses");
        JButton btnAdmin = new JButton("Admin Panel (View Students)");

        btnStudent.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCourses.setFont(new Font("Arial", Font.PLAIN, 18));
        btnAdmin.setFont(new Font("Arial", Font.PLAIN, 18));

        buttonPanel.add(btnStudent);
        buttonPanel.add(btnCourses);
        buttonPanel.add(btnAdmin);

        add(buttonPanel, BorderLayout.CENTER);

        // 🔹 Action Listeners
        btnStudent.addActionListener(e -> registerStudentForm());
        btnCourses.addActionListener(e -> showCourses());
        btnAdmin.addActionListener(e -> showAdminPanel());

        setLocationRelativeTo(null);
        setVisible(true);

        // Ensure courses exist in DB only once
        addFixedCoursesToDB();
    }

    private void addFixedCoursesToDB() {
        try {
            for (Map.Entry<String, Integer> entry : courses.entrySet()) {
                svc.addCourse(entry.getKey(), entry.getValue());
            }
        } catch (Exception ex) {
            // Ignore duplicates
        }
    }

    private void registerStudentForm() {
        JTextField nameField = new JTextField();
        JTextField marksField = new JTextField();
        JTextField emailField = new JTextField();

        JComboBox<String> courseBox = new JComboBox<>(courses.keySet().toArray(new String[0]));

        Object[] fields = {
            "Name:", nameField,
            "Marks (0-100):", marksField,
            "Email:", emailField,
            "Select Course:", courseBox
        };

        if (JOptionPane.showConfirmDialog(this, fields, "Register Student", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int marks = Integer.parseInt(marksField.getText());
                String email = emailField.getText();
                String course = (String) courseBox.getSelectedItem();

                svc.registerStudent(name, marks, email);
                int stdId = svc.getLastInsertedStudentId();
                int courseId = svc.getCourseIdByName(course);
                svc.submitApplication(stdId, courseId);
                svc.processAdmissions();

                // Auto export CSV after every registration
                try {
                    svc.exportAdmissionListCsv("admission_list.csv");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "⚠ Could not export CSV.\nPlease close admission_list.csv if it's open in Excel.",
                        "Export Warning", JOptionPane.WARNING_MESSAGE);
                }

                int cutoff = courses.get(course);
                String status = (marks >= cutoff) ? "APPROVED ✅" : "REJECTED ❌";

                JOptionPane.showMessageDialog(this,
                        "Student Registered!\n" +
                        "Course: " + course + " (Cutoff: " + cutoff + ")\n" +
                        "Marks: " + marks + "\n" +
                        "Admission Status: " + status,
                        "Result", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void showCourses() {
        StringBuilder sb = new StringBuilder("📚 Available Courses & Cutoffs:\n\n");
        for (Map.Entry<String, Integer> entry : courses.entrySet()) {
            sb.append(entry.getKey()).append("  (Cutoff: ").append(entry.getValue()).append(")\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Courses", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAdminPanel() {
        JFrame adminFrame = new JFrame("Admin Panel - Student Applications");
        adminFrame.setSize(800, 400);
        adminFrame.setLayout(new BorderLayout());

        String[] columns = {"Student ID", "Name", "Marks", "Email", "Course", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        try (Connection conn = DriverManager.getConnection(DBConnection.url(), DBConnection.user(), DBConnection.password());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                "SELECT s.std_id, s.name, s.marks, s.email, c.name AS course, a.status " +
                "FROM applications a " +
                "JOIN students s ON a.std_id = s.std_id " +
                "JOIN courses c ON a.course_id = c.course_id")) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("std_id"),
                        rs.getString("name"),
                        rs.getInt("marks"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getString("status")
                };
                model.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading admin panel: " + ex.getMessage());
        }

        adminFrame.add(scrollPane, BorderLayout.CENTER);
        adminFrame.setLocationRelativeTo(this);
        adminFrame.setVisible(true);
    }
}
