package com.quizapp;

import java.sql.*;

public class ResultDAO {
    public void insertResult(int userId, int score) {
        String status = (score >= 5) ? "PASSED" : "FAILED";
        String sql = "INSERT INTO results(user_id, score, status) VALUES (?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, score);
            ps.setString(3, status);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
