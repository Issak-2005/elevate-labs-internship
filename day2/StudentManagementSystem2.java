// You can write both the Student2 class and the StudentManagementSystem2 class in a single file.
// In this case, only one class (StudentManagementSystem2) should be declared as public and the file name must match this public class.
// The other class (Student2) should not be public and will only be accessible within this file (package-private).
// The following code is a complete implementation of the Student Management System with the Student2 class defined within the same file.
package day2;

import java.util.ArrayList;
import java.util.Scanner;

class Student2 {
    private int id;
    private String name;
    private double  marks;

    public Student2(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getMarks() {
        return marks;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setMarks(double marks) {
        this.marks = marks;
    }
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Marks: " + marks;
    }
}
public class StudentManagementSystem2 {
    private static ArrayList<Student2> students = new ArrayList<>();
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
        for (Student2 student : students) {
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
        students.add(new Student2(id, name, marks));
        System.out.println("Student added successfully.");
    }
    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("\nList of Students:");
        System.out.println("-------------------");
        for (Student2 student : students) {
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

        for (Student2 student : students) {
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

        for (Student2 student : students) {
            if (student.getId() == id) {
                students.remove(student);
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
        
    }
}
