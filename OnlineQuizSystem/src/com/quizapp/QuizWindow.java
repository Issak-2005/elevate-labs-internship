package com.quizapp;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizWindow extends JFrame {
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int userId;
    private List<Question> questions;
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JButton nextBtn;

    public QuizWindow(int userId) {
        this.userId = userId;
        QuestionDAO qdao = new QuestionDAO();
        questions = qdao.getAllQuestions();

        setTitle("Quiz - Quiz App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        questionLabel = new JLabel("Question");
        options = new JRadioButton[4]; // FIXED array size
        group = new ButtonGroup();
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            optionsPanel.add(options[i]);
        }

        nextBtn = new JButton("Next");
        nextBtn.addActionListener(e -> checkAnswer());

        setLayout(new BorderLayout());
        add(questionLabel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(nextBtn, BorderLayout.SOUTH);

        loadQuestion();
        setVisible(true);
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question q = questions.get(currentQuestionIndex);
            questionLabel.setText("Q" + (currentQuestionIndex + 1) + ": " + q.getQuestionText());
            options[0].setText(q.getOption1());
            options[1].setText(q.getOption2());
            options[2].setText(q.getOption3());
            options[3].setText(q.getOption4());
            group.clearSelection();
        } else {
            finishQuiz();
        }
    }

    private void checkAnswer() {
        if (currentQuestionIndex >= questions.size()) return;
        Question q = questions.get(currentQuestionIndex);
        String correct = q.getCorrectOption();
        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) selected = i;
        }
        if (selected != -1 && correct.equals(String.valueOf(selected + 1))) {
            score++;
        }
        currentQuestionIndex++;
        loadQuestion();
    }

    private void finishQuiz() {
        new ResultDAO().insertResult(userId, score);
        dispose();
        new ResultWindow(userId, score);
    }
}
