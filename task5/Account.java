package task5;
import java.util.ArrayList;

public class Account implements Bank {
    private ArrayList<Customer> customers = new ArrayList<>();

    // Create new account
    public void createAccount(int accountNumber, String name, double initialBalance) {
        Customer customer = new Customer(name, accountNumber, initialBalance);
        customers.add(customer);
        System.out.println("Account created successfully for " + name);
    }

    // Helper to find customer by account number
    public Customer findCustomer(int accountNumber) {
        for (Customer cus : customers) {
            if (cus.getAccountNumber() == accountNumber) {
                return cus;
            }
        }
        System.out.println("Account not found!");
        return null;
    }

    @Override
    public void deposit(int accountNumber, double amount) {
        Customer customer = findCustomer(accountNumber);
        if (customer != null) {
            customer.setBalance(customer.getBalance() + amount);
            customer.getTransactionHistory().add("Deposited: " + amount);
            System.out.println("Deposit successful! New balance: " + customer.getBalance());
        }
    }

    @Override
    public void withdraw(int accountNumber, double amount) {
        Customer customer = findCustomer(accountNumber);
        if (customer != null) {
            if (amount <= customer.getBalance()) {
                customer.setBalance(customer.getBalance() - amount);
                customer.getTransactionHistory().add("Withdrew: " + amount);
                System.out.println("Withdrawal successful! New balance: " + customer.getBalance());
            } else {
                System.out.println("Insufficient funds");
            }
        }
    }

    @Override
    public void showTransactionHistory(int accountNumber) {
        Customer customer = findCustomer(accountNumber);
        if (customer != null) {
            System.out.println("Transaction history for account " + accountNumber + ":");
            for (String transaction : customer.getTransactionHistory()) {
                System.out.println(transaction);
            }
        }
    }

    @Override
    public void closeAccount(int accountNumber) {
        Customer customer = findCustomer(accountNumber);
        if (customer != null) {
            customers.remove(customer);
            System.out.println("Account " + accountNumber + " closed.");
        }
    }
}