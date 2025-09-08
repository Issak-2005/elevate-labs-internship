package com.quizapp;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserDAO userDAO = new UserDAO();

    public LoginForm() {
        setTitle("Login - Quiz App");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        panel.add(loginBtn);
        panel.add(registerBtn);

        add(panel);

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if (userDAO.validateLogin(user, pass)) {
                int userId = userDAO.getUserId(user);
                dispose();
                new QuizWindow(userId);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login!");
            }
        });

        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterForm();
        });

        setVisible(true);
    }
}
