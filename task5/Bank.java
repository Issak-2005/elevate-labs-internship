package task5  ;
public interface Bank {
    void deposit(int accountNumber, double amount);
    void withdraw(int accountNumber, double amount);
    void showTransactionHistory(int accountNumber);
    void closeAccount(int accountNumber);
}