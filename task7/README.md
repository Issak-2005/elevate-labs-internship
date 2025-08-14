# Java JDBC – Employee Management System  

This folder contains a **Java console-based application** called **Employee Management System** that connects to a **MySQL database** using **JDBC**.  
The app allows adding, viewing, updating, and deleting employee records.  
It is **user-friendly** because it automatically creates the database and table if they do not exist.  

---

## Features  

- Automatic database and table creation (`issak_database` with `employees` table)  
- Add new employee details (Name, Position, Salary)  
- View all employees in a formatted list  
- Update employee details (Position, Salary, or both)  
- Delete employee by ID  
- Interactive, easy-to-use menu-driven interface  

---
## How to Run  
### Using Command Line

1. Compile the program:

   ```
   javac task7/EmployeeApp.java
   ```
2. Run the program:

   ```
   java task7.EmployeeApp
   ```

   
Note: Install suitable mysql-connector-java .jar file.
For me: mysql-connector-j-8.0.33.jar - given in this folder.
---

## Usage

* **Add Employee** – Enter name, position, and salary to insert a new record.
* **View Employees** – Display all employees in the table.
* **Update Employee** – Change position, salary, or both for a given ID.
* **Delete Employee** – Remove a record using the employee ID.
* The app automatically creates the required database and table if they don’t exist.

---

## Example Output

```
 Database ready: issak_database
 Table ready: employees
Connected to MySQL Database

--- Employee Menu ---
1. Add Employee
2. View Employees
3. Update Employee
4. Delete Employee
0. Exit
Choose option:
```

---

## Author

* **Issak Satta**
* Elevate Labs Internship, 2025

---

Thank you for using the **Java JDBC – Employee Management System**!
