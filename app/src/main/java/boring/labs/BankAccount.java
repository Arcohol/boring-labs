package boring.labs;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class BankAccount {
    // associate BankAccount with Customer
    private Customer customer;

    // keep track of transactions
    private List<Transaction> transactions;

    // information about the account
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

        this.accountNumber = generateAccountNumber();
        this.pin = pin;
        this.balance = 0.0;
        this.isSuspended = false;
        this.isClosed = false;
    }

    private String generateAccountNumber() {
        Random random = new Random();
        int accountNumber = random.nextInt(99999999);
        return String.format("%08d", accountNumber);
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

    public void suspend() {
        isSuspended = true;
    }

    public void reinstate() {
        isSuspended = false;
    }

    public void close() {
        isClosed = true;
    }

    // add a deposit to the account
    // if the deposit is cash, add the amount to the balance, and it's immediately cleared
    protected void addDeposit(double amount, DepositType type) {
        statusCheck();

        transactions.add(new Deposit(amount, type));
        if (type == DepositType.CASH) {
            balance += amount;
        }
    }

    // add a withdrawal to the account
    // the balance is directly reduced
    protected void addWithdrawal(double amount) {
        statusCheck();

        transactions.add(new Withdrawal(amount));
        balance -= amount;
    }

    // if the account is suspended or closed, throw an exception
    private void statusCheck() {
        if (isSuspended || isClosed) {
            throw new RuntimeException("account is suspended or closed");
        }
    }

    // clear all deposits
    public void clearFunds() {
        for (Transaction transaction : transactions) {
            if (transaction instanceof Deposit && ((Deposit) transaction).isClear() == false) {
                // if the transaction is a deposit and it's not cleared, clear it
                ((Deposit) transaction).clear();
                balance += transaction.getAmount();
            }
        }
    }

    @Override
    public String toString() {
        return String.format("{%s, %.2f, is_suspended: %b, is_closed: %b}", accountNumber, balance,
                isSuspended, isClosed);
    }
}
