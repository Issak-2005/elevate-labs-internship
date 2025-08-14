package task7;
import java.sql.*;
import java.util.Scanner;

public class EmployeeApp1 {
    private static final String DB_NAME = "issak_database";

    // Connect first to the default "postgres" database to check/create your DB
    private static final String URL_WITHOUT_DB = "jdbc:postgresql://localhost:5432/postgres";
    // Then connect to your actual app database
    private static final String URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;

    private static final String USER = "postgres"; // <-- change if different
    private static final String PASSWORD = "root"; // <-- change if different

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            createDatabaseIfNotExists();
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

                createTableIfNotExists(conn);
                System.out.println("Connected to PostgreSQL Database");
                int choice = -1;

                do {
                    System.out.println("\n--- Employee Menu ---");
                    System.out.println("1. Add Employee");
                    System.out.println("2. View Employees");
                    System.out.println("3. Update Employee");
                    System.out.println("4. Delete Employee");
                    System.out.println("0. Exit");
                    System.out.print("Choose option: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number.");
                        sc.nextLine();
                        continue;
                    }
                    choice = sc.nextInt();
                    sc.nextLine(); // consume newline

                    switch (choice) {
                        case 1:
                            System.out.print("Enter name: ");
                            String name = sc.nextLine();
                            System.out.print("Enter position: ");
                            String position = sc.nextLine();
                            System.out.print("Enter salary: ");
                            while (!sc.hasNextDouble()) {
                                System.out.print("Please enter a valid number for salary: ");
                                sc.next();
                            }
                            double salary = sc.nextDouble();
                            addEmployee(conn, name, position, salary);
                            break;

                        case 2:
                            viewEmployees(conn);
                            break;

                        case 3:
                            updateEmployee(conn);
                            break;

                        case 4:
                            System.out.print("Enter ID to delete: ");
                            while (!sc.hasNextInt()) {
                                System.out.print("Please enter a valid ID: ");
                                sc.next();
                            }
                            int idD = sc.nextInt();
                            deleteEmployee(conn, idD);
                            break;

                        case 0:
                            System.out.println("Goodbye!");
                            System.out.println("Have a great day!");
                            System.out.println("Thank you for using our Employee Management System!");
                            break;

                        default:
                            System.out.println("Invalid option. Try again.");
                    }
                } while (choice != 0);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // Create DB if not exists (PostgreSQL doesn't support CREATE DATABASE IF NOT EXISTS)
    private static void createDatabaseIfNotExists() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL_WITHOUT_DB, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // check existence
            String checkSql = "SELECT 1 FROM pg_database WHERE datname = ?";
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setString(1, DB_NAME);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        // CREATE DATABASE must run outside a transaction; autocommit should be true by default
                        stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
                        System.out.println("Database created: " + DB_NAME);
                    } else {
                        System.out.println("Database ready: " + DB_NAME);
                    }
                }
            }
        }
    }

    // Create table if not exists
    private static void createTableIfNotExists(Connection conn) throws SQLException {
        // Use SERIAL for auto-increment; NUMERIC is preferred alias for DECIMAL in Postgres
        String sql = "CREATE TABLE IF NOT EXISTS employees (" +
                     "id SERIAL PRIMARY KEY, " +
                     "name VARCHAR(100) NOT NULL, " +
                     "position VARCHAR(50), " +
                     "salary NUMERIC(10, 2)" +
                     ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Table ready: employees");
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
            System.out.println("Employee added successfully.");
        }
    }

    private static void viewEmployees(Connection conn) throws SQLException {
        String sql = "SELECT id, name, position, salary FROM employees ORDER BY id";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- Employee List ---");
            boolean any = false;
            while (rs.next()) {
                any = true;
                System.out.printf("%d | %s | %s | %.2f%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"));
            }
            if (!any) System.out.println("(no records)");
        }
    }

    private static void updateEmployee(Connection conn) throws SQLException {
        System.out.print("Enter ID to update: ");
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid ID: ");
            sc.next();
        }
        int idU = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.println("Which one do you want to update? position(p) or salary(s) or both(ps)");
        String updateChoice = sc.nextLine().trim().toLowerCase();

        switch (updateChoice) {
            case "p":
                System.out.print("Enter new position: ");
                String positionU = sc.nextLine();
                updateEmployeePosition(conn, idU, positionU);
                break;
            case "s":
                System.out.print("Enter new salary: ");
                while (!sc.hasNextDouble()) {
                    System.out.print("Please enter a valid number for salary: ");
                    sc.next();
                }
                double salaryU = sc.nextDouble();
                updateEmployeeSalary(conn, idU, salaryU);
                break;
            case "ps":
                System.out.print("Enter new position: ");
                String posBoth = sc.nextLine();
                System.out.print("Enter new salary: ");
                while (!sc.hasNextDouble()) {
                    System.out.print("Please enter a valid number for salary: ");
                    sc.next();
                }
                double salBoth = sc.nextDouble();
                updateEmployee(conn, idU, posBoth, salBoth);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void updateEmployeePosition(Connection conn, int id, String newPosition) throws SQLException {
        String sql = "UPDATE employees SET position = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPosition);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Employee position updated successfully." : "Employee not found.");
        }
    }

    private static void updateEmployeeSalary(Connection conn, int id, double newSalary) throws SQLException {
        String sql = "UPDATE employees SET salary = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newSalary);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Employee salary updated successfully." : "Employee not found.");
        }
    }

    private static void updateEmployee(Connection conn, int id, String position, double salary) throws SQLException {
        String sql = "UPDATE employees SET position = ?, salary = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, position);
            pstmt.setDouble(2, salary);
            pstmt.setInt(3, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Employee updated successfully." : "Employee not found.");
        }
    }

    private static void deleteEmployee(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Employee deleted successfully." : "Employee not found.");
        }
    }
}
