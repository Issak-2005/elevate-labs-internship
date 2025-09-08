package com.quizapp;

public class QuestionSeeder {
    public QuestionSeeder() {
        QuestionDAO dao = new QuestionDAO();

        dao.insertQuestion(new Question(0, "What are Java loops?",
            "Control structures for repetition",
            "Data types", "Packages", "None", "1"));

        dao.insertQuestion(new Question(0, "What is an enhanced for-loop?",
            "Loop with conditions", "For-each loop", "Do-while loop", "Infinite loop", "2"));

        dao.insertQuestion(new Question(0, "What is ArrayList?",
            "A fixed-size array", "Resizable array in Java", "Database table", "Primitive type", "2"));

        dao.insertQuestion(new Question(0, "How to sort a list?",
            "Collections.sort()", "list.sort()", "Bubble sort", "All of the above", "4"));

        dao.insertQuestion(new Question(0, "What is the default value of a boolean variable?",
            "true", "false", "0", "1", "2"));

        dao.insertQuestion(new Question(0, "What is the default value of a float variable?",
            "0.0f", "0.0", "null", "undefined", "1"));

        dao.insertQuestion(new Question(0, "What is the default value of a double variable?",
            "0.0d", "0.0", "null", "undefined", "1"));

        dao.insertQuestion(new Question(0, "Which one of the following is not a Java Feature?",
            "Object-oriented", "Platform-independent", "Use of pointers", "Multithreaded", "3"));

        dao.insertQuestion(new Question(0, "Which keyword is used to define a class in Java?",
            "class", "define", "className", "new", "1"));

        dao.insertQuestion(new Question(0, "Which keyword is used to inherit a class in Java?",
            "extends", "inherits", "implements", "super", "1"));
    }
}
