package day3;
import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books= new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    

    public Library() {
        // Initialize with some books if needed
        // books.add(new Book("Sample Book", "Sample Author"));
    }

    public void addBook(Book book) {
        books.add(book);
    }
    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }
        System.out.println("--------------------");
        for (Book book : books) {
            System.out.println("Title: " + book.getTitle() + ", \nAuthor: " + book.getAuthor() + (book.isIssued() ? " (Issued)" : " (Available)"));
            System.out.println("--------------------");
        }
    }
    public void removeBook(String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equalsIgnoreCase(title)) {
                books.remove(i);
                System.out.println("Book '" + title + "' removed successfully.");
                return;
            }
        }
        System.out.println("Book not found.");
    }
    public void issueBook(String title, User user) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (!book.isIssued()) {
                    book.setIssued(true);
                    book.setIssuedTo(user); // Assuming 'user' is an instance of User class
                    System.out.println("Book '" + title + "' is issued to " + user.getName() + ".");
                } else {
                    System.out.println("Book is already issued.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }
    public void returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isIssued()) {
                    book.setIssued(false);
                    System.out.println("Book returned successfully.");
                } else {
                    System.out.println("Book was not issued.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }
    public ArrayList<Book> getBooks() {
        return books;
    }   
    public User getIssuedTo() {
        for (Book book : books) {
            if (book.isIssued()) {
                return book.getIssuedTo();
            }
        }
        return null;
    }
    public void addUser(User user) {
        boolean userExists = false;
        for (User existingUser : users) {
            if (existingUser.getName().equalsIgnoreCase(user.getName()) && existingUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                userExists = true;
                break;
            }
        }
        if (!userExists) {
            users.add(user);
            System.out.println("User '" + user.getName() + "' added successfully.");
        } else {
            System.out.println("User with this name and email already exists.");
        }
    }
    public ArrayList<User> getUsers() {
        return users;
    }
}
