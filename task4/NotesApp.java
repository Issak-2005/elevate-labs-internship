package task4;
import java.io.*;
import java.util.Scanner;

public class NotesApp {
    private static  String APP_NAME ;
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
       
        // NotesApp notesApp = new NotesApp();
        System.out.println("Welcome to the Notes Application!");
        // System.out.println("App Name: " + APP_NAME);
        
        int choice;
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Delete File");
            System.out.println("4. Rename File");
            System.out.println("5. View all files(.txt)");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            switch (choice) {
                case 1:
                    addNote();
                    break;
                case 2:
                    viewNotes();
                    break;
                case 3:
                    deleteFile();
                    break;
                case 4:
                    renameFile();
                    break;
                case 5:
                    viewAllFiles();
                    break; 
                case 6:
                    System.out.println("Exited");
                    System.out.println("Thank you for using the Notes Application!");
                    System.out.println("Have a great day!");
                    System.out.println("Visit again!");
                    return; // Exit the application 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } 
        // System.out.println("Exiting the Notes Application. Goodbye!");
    }
    private static void addNote() {
        System.out.println("Enter file name to save notes:");
        APP_NAME = scan.nextLine();
        if (!APP_NAME.endsWith(".txt")) {
            APP_NAME ="task4/"+APP_NAME+".txt"; // Ensure the file has a .txt extension
        } else {
            APP_NAME = "task4/" + APP_NAME; // Ensure the file is in the task4 directory
        }
        System.out.println("Do you want to create a new file or append to an existing one? (new/append)");
        String fileMode = scan.nextLine();
        System.out.print("Enter your note: ");
        String note = scan.nextLine();
        if (fileMode.equals("new")) {
            try (FileWriter fw = new FileWriter(APP_NAME, false); // false to overwrite
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(note);
                System.out.println("Note written to " + APP_NAME);
            } catch (IOException e) {
                System.out.println("Error writing note: " + e.getMessage());
            }
        } else if (fileMode.equals("append")) {
            try (FileWriter fw = new FileWriter(APP_NAME, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(note);
                System.out.println("Note appended to " + APP_NAME);
            } catch (IOException e) {
                System.out.println("Error writing note: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid file mode. Please enter 'new' or 'append'.");
        }
    
    }

    private static void viewNotes() {
        System.out.println("Enter file name to view notes:");
        APP_NAME = scan.nextLine();
        if (!APP_NAME.endsWith(".txt")) {
           APP_NAME = "task4/" + APP_NAME + ".txt";
         } else {
             APP_NAME = "task4/" + APP_NAME;
        }
        System.out.println("Viewing all notes...");
        try (BufferedReader br = new BufferedReader(new FileReader(APP_NAME))) {    
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading notes: " + e.getMessage());
        }
    }
    private static void deleteFile() {
        System.out.print("Enter the file name to delete(only .txt files): ");
        String fileToDelete = scan.nextLine();
        if (!fileToDelete.endsWith(".txt")) {
            fileToDelete = "task4/" + fileToDelete + ".txt"; // Ensure the file has a .txt extension
        } else {
            fileToDelete = "task4/" + fileToDelete; // Ensure the file is in the task4 directory
        }
        File file = new File(fileToDelete);
        if (file.delete()) {
            System.out.println("File deleted: " + fileToDelete);
        } else {
            System.out.println("File not found or could not be deleted: " + fileToDelete);
        }
    }
    private static void renameFile() {
        System.out.print("Enter current file name: ");
        String currentFileName = scan.nextLine();
        if (!currentFileName.endsWith(".txt")) {
            currentFileName = "task4/" + currentFileName + ".txt";
        } else {
            currentFileName = "task4/" + currentFileName;
        }

        System.out.print("Enter new file name: ");
        String newFileName = scan.nextLine();
        if (!newFileName.endsWith(".txt")) {
            newFileName = "task4/" + newFileName + ".txt";
        } else {
            newFileName = "task4/" + newFileName;
        }

        File oldFile = new File(currentFileName);
        File newFile = new File(newFileName);

        if (oldFile.exists()) {
            if (oldFile.renameTo(newFile)) {
                System.out.println("File renamed to: " + newFile.getName());
            } else {
                System.out.println("Failed to rename file.");
            }
        } else {
            System.out.println("Current file does not exist.");
        }
    }
    private static void viewAllFiles() {
        File folder = new File("task4");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (listOfFiles != null && listOfFiles.length > 0) {
            System.out.println("Text files in directory:");
            for (File file : listOfFiles) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("No text files found.");
        }
    }
}
