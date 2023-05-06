package boring.labs;

import java.util.Random;

public abstract class BankAccount {
    private String accountNumber;
    private String pin;
    private double balance;
    private boolean isSuspended;
    private boolean isClosed;

    public BankAccount(String pin) {
        setAccountNumber();
        setPin(pin);
        this.balance = 0.0;
        this.isSuspended = false;
        this.isClosed = false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public boolean isClosed() {
        return isClosed;
    }

    private void setAccountNumber() {
        Random random = new Random();
        int accountNumber = random.nextInt(99999999);
        this.accountNumber = String.format("%08d", accountNumber);
    }

    private void setPin(String pin) {
        // TODO: validate pin
        // It should be 4 digits

        this.pin = pin;
    }

    public void suspend() {
        isSuspended = true;
    }

    public void resume() {
        isSuspended = false;
    }

    public void close() {
        isClosed = true;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public String toString() {
        return String.format("{%s, %.2f}", accountNumber, balance);
    }
}
