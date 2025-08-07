package day3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        // Initialize with some users if needed
        // users.add(new User("John Doe", "john@example.com"));
        User user = new User(); // Initialize user with default constructor
        Scanner scanner = new Scanner(System.in);
        library.addBook(new Book("1984", "George Orwell"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee"));
        library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        library.addBook(new Book("Moby Dick", "Herman Melville"));
        library.addBook(new Book("War and Peace", "Leo Tolstoy"));
        library.addBook(new Book("Pride and Prejudice", "Jane Austen"));
        library.addBook(new Book("Java Basics", "John Doe"));
        library.addBook(new Book("Effective Java", "Joshua Bloch"));
        library.addBook(new Book("Clean Code", "Robert C. Martin"));
        library.addBook(new Book("OOP Concepts", "Bjarne Stroustrup"));
        library.addBook(new Book("OOP in Java", "John Smith"));
        library.addBook(new Book("Design Patterns", "Erich Gamma"));
        library.addBook(new Book("Introduction to Algorithms", "Thomas H. Cormen"));


        System.out.println("\n---- Welcome to the Library Management System! ----");

        while (true) {
            System.out.println("\n\nLibrary Menu:");
        
            System.out.println("1. View Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Add new Book");
            System.out.println("5. Remove Book");
            System.out.println("6. Add User");
            System.out.println("7. View Users");
            System.out.println("8. Delete User");
            System.out.println("9. Issued books");
            System.out.println("10. Remaining books (Excluding issued books)");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
           

            switch (choice) {
                case 1:
                    library.viewBooks();
                    break;
                case 2:
                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter user email: ");
                    String email = scanner.nextLine();    
                    User user2 = new User(name, email);               
                    library.addUser(user2);
                    System.out.print("Enter book title to issue: ");
                    String issueTitle = scanner.nextLine();
                    library.issueBook(issueTitle, user2);
                    break;
                case 3:
                    System.out.print("Enter book title to return: ");
                    String returnTitle = scanner.nextLine();
                    library.returnBook(returnTitle);
                    break;
                case 4:
                    System.out.print("Enter new book title: ");
                    String bookTitle = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String bookAuthor = scanner.nextLine();
                    library.addBook(new Book(bookTitle, bookAuthor));
                    System.out.println("Book '" + bookTitle + "' by " + bookAuthor + " added successfully.");
                    break;
                case 5:
                    System.out.print("Enter book title to remove: ");
                    String removeTitle = scanner.nextLine();
                    library.removeBook(removeTitle);
                    break;
                case 6:
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter user email: ");
                    String userEmail = scanner.nextLine();
                    library.addUser(new User(userName, userEmail));
                    break;
                case 7:
                    System.out.println("Registered Users:");
                    System.out.println("--------------------");
                    if (library.getUsers().isEmpty()) {
                        System.out.println("No users registered.");
                        break;
                    }
                    for (User user1 : library.getUsers()) {
                        System.out.println("Name: " + user1.getName() + ", Email: " + user1.getEmail());
                        System.out.println("--------------------");
                    }
                    break;
                case 8:
                    System.out.print("Enter user email to delete: ");
                    String emailToDelete = scanner.nextLine();
                    library.getUsers().removeIf(u -> u.getEmail().equalsIgnoreCase(emailToDelete));
                    System.out.println("User deleted successfully.");
                    break;
                case 9:
                    System.out.println("Issued Books:");
                    System.out.println("--------------------");
                    for (Book book : library.getBooks()) {
                        if (book.isIssued()) {
                            System.out.println("Title: " + book.getTitle() + ", Issued To: " + book.getIssuedTo().getName());
                            System.out.println("--------------------");
                        }
                    }
                    break;
                case 10:
                    System.out.println("Remaining Books (Excluding Issued Books):");
                    System.out.println("--------------------");
                    for (Book book : library.getBooks()) {
                        if (!book.isIssued()) {
                            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor());
                            System.out.println("--------------------");
                        }
                    }
                    break;
                case 11:
                    System.out.println("Exited from the Library Management System.");
                    System.out.println("Thank you for using the Library Management System!");
                    System.out.println("Have a great day!");
                    System.out.println("Visit again!");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}