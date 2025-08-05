package day2;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagementSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n---- Student Management System ----");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scan.nextInt();
            scan.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }
    private static void addStudent() {
        // Student student = new Student();
        System.out.print("Enter Student ID (numerical): ");
        int id = scan.nextInt();
        scan.nextLine();  // Consume newline
        // Check if ID already exists
        for (Student student : students) {
            if (student.getId() == id) {
                System.out.println("Student with ID " + id + " already exists. Try with a different ID.");
                return;
            }
        }
        System.out.print("Enter Student Name: ");
        String name = scan.nextLine();
        System.out.print("Enter Student Marks: ");
        double marks = scan.nextDouble();
        scan.nextLine();  // Consume newline
        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully.");
    }
    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("\nList of Students:");
        System.out.println("-------------------");
        for (Student student : students) {
            System.out.println(student);
            System.out.println("-------------------");
        }
    }
    private static void updateStudent() {
        System.out.print("Enter Student ID (numerical) to update: ");
        int id = scan.nextInt();
        scan.nextLine();  // Consume newline
        System.out.println("Do you want to update the name and marks? (n/m/nm)");
        String updateChoice = scan.nextLine().toLowerCase();

        for (Student student : students) {
            if (student.getId() == id) {
                if (updateChoice.equals("n")) {
                    System.out.print("Enter new Name: ");
                    String name = scan.nextLine();
                    student.setName(name);
                    System.out.println("Name updated successfully.");
                } else if (updateChoice.equals("m")) {
                    System.out.print("Enter new marks: ");
                    double marks = scan.nextDouble();
                    scan.nextLine();  // Consume newline
                    student.setMarks(marks);
                    System.out.println("Marks updated successfully.");
                } else if (updateChoice.equals("nm")) {
                    System.out.print("Enter new Name: ");
                    String name = scan.nextLine();
                    System.out.print("Enter new marks: ");
                    double marks = scan.nextDouble();
                    scan.nextLine();  // Consume newline
                    student.setName(name);
                    student.setMarks(marks);
                    System.out.println("Name and Marks updated successfully.");
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
    private static void deleteStudent() {
        System.out.print("Enter Student ID (numerical) to delete: ");
        int id = scan.nextInt();
        scan.nextLine();  // Consume newline

        for (Student student : students) {
            if (student.getId() == id) {
                students.remove(student);
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
        
    }
}