package boring.labs;

public abstract class BankAccount {
    private String accountNumber;
    private String accountName;
    private double balance;
    private boolean isSuspended;

    public BankAccount(String accountNumber, String accountName) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = 0.0;
        this.isSuspended = false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void suspend() {
        isSuspended = true;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public String toString() {
        return String.format("{%s, %s, %.2f}", accountNumber, accountName, balance);
    }
}
