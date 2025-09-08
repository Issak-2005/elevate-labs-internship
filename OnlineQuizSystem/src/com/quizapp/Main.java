package com.quizapp;

public class Main {
    public static void main(String[] args) {
        try {
            new DBConnection(); // ensures DB + tables exist
            javax.swing.SwingUtilities.invokeLater(() -> {
                new LoginForm(); // start with login window
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
