package task7;
import java.sql.*;
import java.util.Scanner;

public class EmployeeApp {
    private static final String DB_NAME = "issak_database";
    private static final String URL_WITHOUT_DB = "jdbc:mysql://localhost:3306/?serverTimezone=UTC";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?serverTimezone=UTC";
    private static final String USER = "root"; // Change
    private static final String PASSWORD = "root"; // Change
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            createDatabaseIfNotExists();
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

                createTableIfNotExists(conn);
                System.out.println("Connected to MySQL Database ");
                int choice = -1;

                do{
                    System.out.println("\n--- Employee Menu ---");
                    System.out.println("1. Add Employee");
                    System.out.println("2. View Employees");
                    System.out.println("3. Update Employee");
                    System.out.println("4. Delete Employee");
                    System.out.println("0. Exit");
                    System.out.print("Choose option: ");

                    choice = sc.nextInt();
                    sc.nextLine(); // consume newline

                    switch (choice) {
                        case 1 :
                            System.out.print("Enter name: ");
                            String name = sc.nextLine();
                            System.out.print("Enter position: ");
                            String position = sc.nextLine();
                            System.out.print("Enter salary: ");
                            double salary = sc.nextDouble();
                            addEmployee(conn, name, position, salary);
                            break;
                        
                        case 2 : viewEmployees(conn); break;
                        case 3 : {
                            
                            updateEmployee(conn);
                            break; 
                        }
                        case 4 : {
                            System.out.print("Enter ID to delete: ");
                            int idD = sc.nextInt();
                            deleteEmployee(conn, idD);
                            break;
                        }
                        case 0 : {
                            System.out.println("Goodbye!");
                            System.out.println("Have a great day!");
                            System.out.println("Thank you for using our Employee Management System!");
                            break; // Exit the application
                        }
                        default: System.out.println("Invalid option. Try again.");
                    }
                }while(choice!=0);
            }
        } catch (SQLException e) {
            System.out.println(" Database error: " + e.getMessage());
        }
    }

    // Create DB if not exists
    private static void createDatabaseIfNotExists() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL_WITHOUT_DB, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println(" Database ready: " + DB_NAME);
        }
    }

    // Create table if not exists
    private static void createTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS employees (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, position VARCHAR(50), salary DECIMAL(10, 2));";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println(" Table ready: employees");
        }
    }

    // CRUD operations
    private static void addEmployee(Connection conn, String name, String position, double salary) throws SQLException {
        String sql = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salary);
            pstmt.executeUpdate();
            System.out.println(" Employee added successfully.");
        }
    }

    private static void viewEmployees(Connection conn) throws SQLException {
        String sql = "SELECT * FROM employees";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("\n--- Employee List ---");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %.2f%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"));
            }
        }
    }

    private static void updateEmployee(Connection conn) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID to update: ");
        int idU = sc.nextInt();
        sc.nextLine();
        System.out.println("Which one you want to update position(p) or salary(s) or both(ps)");
        String updateChoice = sc.nextLine();
        if (updateChoice.equals("p")) {
            System.out.print("Enter new position: ");
            String positionU = sc.nextLine();
            updateEmployeePosition(conn, idU, positionU);
        } else if (updateChoice.equals("s")) {
            System.out.print("Enter new salary: ");
            double salaryU = sc.nextDouble();
            updateEmployeeSalary(conn, idU, salaryU);
        } else if (updateChoice.equals("ps")) {
            System.out.print("Enter new position: ");
            String positionU = sc.nextLine();
            System.out.print("Enter new salary: ");
            double salaryU = sc.nextDouble();
            updateEmployee(conn, idU, positionU, salaryU);
        } else {
            System.out.println("Invalid option.");
        }
    }

    private static void updateEmployeePosition(Connection conn, int id, String newPosition) throws SQLException {
        String sql = "UPDATE employees SET position=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPosition);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? " Employee position updated successfully." : "⚠ Employee not found.");
        }
    }

    private static void updateEmployeeSalary(Connection conn, int id, double newSalary) throws SQLException {
        String sql = "UPDATE employees SET salary=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newSalary);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? " Employee salary updated successfully." : "⚠ Employee not found.");
        }
    }

    private static void updateEmployee(Connection conn, int id, String position, double salary) throws SQLException {
        String sql = "UPDATE employees SET position=?, salary=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, position);
            pstmt.setDouble(2, salary);
            pstmt.setInt(3, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? " Employee updated successfully." : "⚠ Employee not found.");
        }
    }

    private static void deleteEmployee(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? " Employee deleted successfully." : "⚠ Employee not found.");
        }
    }
}
