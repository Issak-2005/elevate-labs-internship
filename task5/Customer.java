package task5;
import java.util.ArrayList;
public class Customer {
    private String name;
    private int accountNumber;
    private double balance;
    private ArrayList<String> transactionHistory;

    public Customer(String name, int accountNumber, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with balance: " + balance);
    }

    public String getName() { return name; }
    public int getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public ArrayList<String> getTransactionHistory() { return transactionHistory; }
}