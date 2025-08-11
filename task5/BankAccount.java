package task5;
import java.util.Scanner;
public class BankAccount {
    public static void main(String args[])  {
        Account bank = new Account();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== Bank Account Management System =====");
            System.out.println("1. Add a new account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Show Transaction History");
            System.out.println("5. Show Balance");
            System.out.println("6. Close Account");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    int accountNumber = scanner.nextInt();
                    System.out.print("Enter account holder name: ");
                    String name = scanner.next();
                    System.out.print("Enter initial balance: ");
                    double initialBalance = scanner.nextDouble();
                    bank.createAccount(accountNumber, name, initialBalance);
                    break;

                case 2:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextInt();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    bank.deposit(accountNumber, depositAmount);
                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextInt();
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    bank.withdraw(accountNumber, withdrawAmount);
                    break;

                case 4:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextInt();
                    bank.showTransactionHistory(accountNumber);
                    break;

                case 5:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextInt();
                    Customer customer = bank.findCustomer(accountNumber);
                    if (customer != null) {
                        System.out.println("Current Balance: " + customer.getBalance());
                    }
                    break;

                case 6:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextInt();
                    bank.closeAccount(accountNumber);
                    break;

                case 0:
                    System.out.println("Exiting... ");
                    System.out.println("Goodbye!");
                    System.out.println("Have a great day!");
                    System.out.println("Thank you for using our banking services!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);

        scanner.close();
        return;
    }
}
