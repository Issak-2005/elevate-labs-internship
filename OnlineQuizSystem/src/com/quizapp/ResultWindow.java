package com.quizapp;

import javax.swing.*;
import java.awt.*;

public class ResultWindow extends JFrame {
    public ResultWindow(int userId, int score) {
        setTitle("Quiz Result");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("Your Score: " + score));
        panel.add(new JLabel(score >= 5 ? "Status: PASSED" : "Status: FAILED"));

        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> System.exit(0));
        panel.add(exitBtn);

        add(panel);
        setVisible(true);
    }
}
