package com.quizapp;

import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserDAO userDAO = new UserDAO();

    public RegisterForm() {
        setTitle("Register - Quiz App");
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

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");
        panel.add(registerBtn);
        panel.add(backBtn);

        add(panel);

        registerBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if (userDAO.create(user, pass)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                dispose();
                new LoginForm();
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists!");
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

        setVisible(true);
    }
}
