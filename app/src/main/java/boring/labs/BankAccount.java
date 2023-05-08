package boring.labs;

import java.util.LinkedList;
import java.util.Random;

public abstract class BankAccount {
    private Customer customer;

    private LinkedList<Transaction> transactions;

    private String accountNumber;
    private String pin;
    private double balance;
    private boolean isSuspended;
    private boolean isClosed;

    public BankAccount(String pin, Customer customer) {
        if (customer == null) {
            throw new RuntimeException("customer cannot be null");
        }

        if (customer.getCreditStatus() == false) {
            throw new RuntimeException("credit check failed");
        }

        this.customer = customer;
        this.transactions = new LinkedList<>();
        setAccountNumber();
        setPin(pin);
        this.balance = 0.0;
        this.isSuspended = false;
        this.isClosed = false;
    }

    public Customer getCustomer() {
        return customer;
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
        // It should be 6 digits
        this.pin = pin;
    }

    public void suspend() {
        isSuspended = true;
    }

    public void reinstate() {
        isSuspended = false;
    }

    public void close() {
        isClosed = true;
    }

    protected void addDeposit(double amount) {
        check();
        transactions.add(new Deposit(amount));
    }

    protected void addWithdrawal(double amount) {
        check();
        transactions.add(new Withdrawal(amount));
        balance -= amount;
    }

    public String toString() {
        return String.format("{%s, %.2f}", accountNumber, balance);
    }

    public void check() {
        if (isSuspended || isClosed) {
            throw new RuntimeException("account is suspended or closed");
        }
    }

    public void clearFunds() {
        for (Transaction transaction : transactions) {
            if (transaction instanceof Deposit) {
                balance += transaction.getAmount();
                ((Deposit) transaction).clear();
            }
        }
    }
}
