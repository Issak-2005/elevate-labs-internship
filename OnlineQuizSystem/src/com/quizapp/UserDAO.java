package com.quizapp;

import java.sql.*;

public class UserDAO {
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return new User(rs.getInt("user_id"),
                                    rs.getString("username"),
                                    rs.getString("password"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean create(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES(?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            return ps.executeUpdate() == 1;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false; // username exists
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean validateLogin(String username, String password) {
        String sql = "SELECT 1 FROM users WHERE username=? AND password=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public int getUserId(String username) {
        String sql = "SELECT user_id FROM users WHERE username=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }
}
