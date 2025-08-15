// package task8;
import java.util.*;


class Question {
    private String question;
    private List<String> options;
    private int correctOption;

    public Question(String question, List<String> options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public void displayQuestion() {
        System.out.println(question);
        for (int i = 0; i < options.size(); i++) {
            System.out.print((i + 1) + ". " + options.get(i) + "\t");
        }
    }

    public boolean isCorrect(int userChoice) {
        return userChoice == correctOption;
    }
    public String getQuestionText() {
        return question;
    }
    public List<String> getOptions() {
        return options;
    }
    public int getCorrectOption() {
        return correctOption;
    }
}

public class QuizApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Question> questions = new ArrayList<>();

        // Adding quiz questions
        questions.add(new Question(
                "What are Java loops?",
                Arrays.asList("Control structures for repetition", "Data types", "Packages", "None"),
                1
        ));
        questions.add(new Question(
                "What is an enhanced for-loop?",
                Arrays.asList("Loop with conditions", "For-each loop", "Do-while loop", "Infinite loop"),
                2
        ));
        questions.add(new Question(
                "What is ArrayList?",
                Arrays.asList("A fixed-size array", "Resizable array in Java", "Database table", "Primitive type"),
                2
        ));
        questions.add(new Question(
                "How to sort a list?",
                Arrays.asList("Collections.sort()", "list.sort()", "Bubble sort", "All of the above"),
                4
        ));
        questions.add(new Question(
                "What is the default value of a boolean variable?",
                Arrays.asList("true", "false", "0", "1"),
                2
        ));
        questions.add(new Question(
                "What is the default value of a float variable?",
                Arrays.asList("0.0f", "0.0", "null", "undefined"),
                1
        ));
        questions.add(new Question(
                "What is the default value of a double variable?",
                Arrays.asList("0.0d", "0.0", "null", "undefined"),
                1
        ));
        questions.add(new Question(
                "Which one of the following is not a Java Feature?",
                Arrays.asList("Object-oriented", "Platform-independent", "Use of pointers", "Multithreaded"),
                3
        ));
        questions.add(new Question(
                "Which keyword is used to define a class in Java?",
                Arrays.asList("class", "define", "className", "new"),
                1
        ));
        questions.add(new Question(
                "Which keyword is used to inherit a class in Java?",
                Arrays.asList("extends", "inherits", "implements", "super"),
                1
        ));


        int score = 0;

        System.out.println("\n\n=== Welcome to the Online Quiz App ===\n");
        System.out.print("Enter your name:");
        String userName = sc.nextLine();
        System.out.println("Please answer the following questions:\n");

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("Q" + (i + 1) + ":");
            q.displayQuestion();

            System.out.print("\nYour answer (1-4): ");
            int userChoice;
            try {
                userChoice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Skipping question.\n");
                sc.next(); // clear invalid input
                continue;
            }

            if (q.isCorrect(userChoice)) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Wrong!\n");
            }
        }

        System.out.println("=== Quiz Over ===");
        System.out.println("Thank you for participating, " + userName + "!");
        System.out.println("Your score: " + score + "/" + questions.size());
        if (score == questions.size()) {
            System.out.println("Congratulations! You answered all questions correctly.");
        }else{
            System.out.println("\n Correct Answers are:");
            System.out.println("---------------------------");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Question q : questions) {
                System.out.println("Q: " + q.getQuestionText());
                System.out.println("Correct Answer: " + q.getOptions().get(q.getCorrectOption() - 1));
                System.out.println("---------------------------");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        sc.close();
    }
}
