package day1;

import java.util.Scanner;

class Calculator {

    // Addition method using varargs
    public static void addition(double... numbers) {
        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }
        System.out.println("Addition value is: " + sum);
    }

    // Subtraction method
    public static void subtraction(double... numbers) {
        if (numbers.length == 0) {
            System.out.println("No numbers provided for subtraction.");
            return;
        }
        double result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            result -= numbers[i];
        }
        System.out.println("Subtraction value is: " + result);
    }

    // Multiplication method
    public static void multiplication(double... numbers) {
        double product = 1;
        for (double num : numbers) {
            product *= num;
        }
        System.out.println("Multiplication value is: " + product);
    }

    // Division method
    public static void division(double... numbers) {
        if (numbers.length == 0) {
            System.out.println("No numbers provided for division.");
            return;
        }
        double result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] == 0) {
                System.out.println("Division by zero error.");
                return;
            }
            result /= numbers[i];
        }
        System.out.printf("Division value is: %.4f%n", result);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        char choice = 'y';

        while (choice == 'y' || choice == 'Y') {
            System.out.print("\nAddition(add) \nSubtraction(subtract) \nMultiplication(multiply) \nDivision(divide) \nEnter the operation you want to perform: ");
            String operation = scan.nextLine().toLowerCase();

            System.out.println("Enter the numbers (use space as separator): ");
            String[] input = scan.nextLine().split(" ");
            double[] numbers = new double[input.length];

            for (int i = 0; i < input.length; i++) {
                numbers[i] = Double.parseDouble(input[i]);
            }

            switch(operation) {
                case "add":
                    addition(numbers);
                    break;
                case "subtract":
                    subtraction(numbers);
                    break;
                case "multiply":
                    multiplication(numbers);
                    break;
                case "divide":
                    division(numbers);
                    break;
                default:
                    System.out.println("Invalid operation.");
            }

            System.out.println("Do you want to perform another operation? (y/n): \nType 'y' for yes or 'n' for no and press Enter.");
            choice = scan.nextLine().charAt(0);
        }

        scan.close();
        System.out.println("Calculator exited.");
        System.out.println("Thank you for using the calculator!");
    }
}
