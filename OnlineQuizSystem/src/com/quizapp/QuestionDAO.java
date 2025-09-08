package com.quizapp;

import java.sql.*;
import java.util.*;

public class QuestionDAO {
    public void insertQuestion(Question q) {
        String sql = "INSERT INTO questions(question_text, option1, option2, option3, option4, correct_option) VALUES (?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, q.getQuestionText());
            ps.setString(2, q.getOption1());
            ps.setString(3, q.getOption2());
            ps.setString(4, q.getOption3());
            ps.setString(5, q.getOption4());
            ps.setString(6, q.getCorrectOption());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Question> getAllQuestions() {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM questions";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Question(
                        rs.getInt("question_id"),
                        rs.getString("question_text"),
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getString("option4"),
                        rs.getString("correct_option")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
