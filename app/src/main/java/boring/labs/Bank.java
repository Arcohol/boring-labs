package boring.labs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

enum AccountType {
    CURRENT, JUNIOR, SAVER
}


public class Bank {
    private HashMap<String, BankAccount> accounts;
    private List<Customer> customers;

    public Bank() {
        this.accounts = new HashMap<>();
        this.customers = new LinkedList<>();
    }

    private BankAccount getAccount(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);

        if (account == null) {
            throw new RuntimeException("account not found");
        }

        return account;
    }

    private BankAccount getAccount(String accountNumber, String pin) {
        BankAccount account = getAccount(accountNumber);

        if (account.getPin().equals(pin) == false) {
            throw new RuntimeException("pin incorrect");
        }

        return account;
    }

    private void verifyCustomer(Customer customer) {
        if (customer == null) {
            throw new RuntimeException("customer cannot be null");
        }

        if (customer.getCreditStatus() == false) {
            throw new RuntimeException("credit check failed");
        }
    }

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new RuntimeException("customer cannot be null");
        }

        if (customer.getCreditStatus() == false) {
            throw new RuntimeException("credit check failed");
        }

        customers.add(customer);
    }

    public String openAccount(Customer customer, String pin, AccountType type) {
        verifyCustomer(customer);

        // try creating an account, check if its account number is already in use
        BankAccount account = null;
        do {
            switch (type) {
                case CURRENT -> account = new CurrentAccount(pin, customer);
                case JUNIOR -> account = new JuniorAccount(pin, customer);
                case SAVER -> account = new SaverAccount(pin, customer);
            }
        } while (accounts.containsKey(account.getAccountNumber()));
        customer.addAccount(account);
        accounts.put(account.getAccountNumber(), account);
        return account.getAccountNumber();
    }

    public void closeAccount(Customer customer, String accountNumber, String pin) {
        verifyCustomer(customer);

        BankAccount account = customer.getAccount(accountNumber, pin);

        account.close();
    }

    public void suspendAccount(Customer customer, String accountNumber, String pin) {
        verifyCustomer(customer);

        BankAccount account = customer.getAccount(accountNumber, pin);

        account.suspend();
    }

    public void reinstateAccount(Customer customer, String accountNumber, String pin) {
        verifyCustomer(customer);

        BankAccount account = customer.getAccount(accountNumber, pin);

        account.reinstate();
    }


    public void deposit(String accountNumber, double amount, TransactionType type) {
        BankAccount account = getAccount(accountNumber);

        account.addDeposit(amount, type);
    }

    public void withdraw(String accountNumber, String pin, double amount) {
        BankAccount account = getAccount(accountNumber, pin);

        // cast the account to its actual type
        if (account instanceof JuniorAccount) {
            JuniorAccount juniorAccount = (JuniorAccount) account;
            juniorAccount.addWithdrawal(amount);
        } else if (account instanceof CurrentAccount) {
            CurrentAccount currentAccount = (CurrentAccount) account;
            currentAccount.addWithdrawal(amount);
        } else {
            throw new RuntimeException("unknown account type");
        }
    }

    public void clearFunds() {
        for (BankAccount account : accounts.values()) {
            account.clearFunds();
        }
    }

    public double checkBalance(String accountNumber, String pin) {
        BankAccount account = getAccount(accountNumber, pin);

        return account.getBalance();
    }
}
