package boring.labs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Bank {
    // accounts is a map of account numbers to accounts
    private HashMap<String, BankAccount> accounts;

    // customers is a list of customers
    private List<Customer> customers;

    public Bank() {
        this.accounts = new HashMap<>();
        this.customers = new LinkedList<>();
    }

    // get account without pin
    private BankAccount getAccount(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);

        if (account == null) {
            throw new RuntimeException("account not found");
        }

        return account;
    }

    // get account with pin
    private BankAccount getAccount(String accountNumber, String pin) {
        BankAccount account = getAccount(accountNumber);

        if (account.getPin().equals(pin) == false) {
            throw new RuntimeException("pin incorrect");
        }

        return account;
    }

    // customer credit check
    private void validateCustomer(Customer customer) {
        if (customer.getCreditStatus() == false) {
            throw new RuntimeException("credit check failed");
        }
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String openAccount(Customer customer, String pin, AccountType type) {
        validateCustomer(customer);

        // try creating an account, check if its account number is already in use
        BankAccount account = null;
        do {
            switch (type) {
                case CURRENT -> account = new CurrentAccount(pin, customer);
                case JUNIOR -> {
                    // only customers under 16 can open a junior account
                    if (customer.getAge() < 16) {
                        throw new RuntimeException("customer is not a junior");
                    }

                    account = new JuniorAccount(pin, customer);
                }
                case SAVER -> account = new SaverAccount(pin, customer);
            }
        } while (accounts.containsKey(account.getAccountNumber()));

        // add the account to the customer and the bank
        customer.addAccount(account);
        accounts.put(account.getAccountNumber(), account);

        // return the account number
        return account.getAccountNumber();
    }

    public void closeAccount(Customer customer, String accountNumber, String pin) {
        validateCustomer(customer);

        BankAccount account = customer.getAccount(accountNumber, pin);

        // if the account is not empty, throw an exception
        if (account.getBalance() != 0) {
            throw new RuntimeException("account is not empty");
        }

        account.close();
    }

    public void suspendAccount(Customer customer, String accountNumber, String pin) {
        validateCustomer(customer);

        customer.getAccount(accountNumber, pin).suspend();
    }

    public void reinstateAccount(Customer customer, String accountNumber, String pin) {
        validateCustomer(customer);

        customer.getAccount(accountNumber, pin).reinstate();
    }

    // you can deposit to any account without pin
    public void deposit(String accountNumber, double amount, DepositType type) {
        getAccount(accountNumber).addDeposit(amount, type);
    }

    // you have to provide the pin to withdraw
    public void withdraw(String accountNumber, String pin, double amount) {
        getAccount(accountNumber, pin).addWithdrawal(amount);
    }

    // clear funds for all accounts
    public void clearFunds() {
        for (BankAccount account : accounts.values()) {
            account.clearFunds();
        }
    }

    public double checkBalance(String accountNumber, String pin) {
        return getAccount(accountNumber, pin).getBalance();
    }
}
