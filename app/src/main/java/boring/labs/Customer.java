package boring.labs;

import java.time.LocalDate;
import java.util.HashMap;

public class Customer {
    private HashMap<String, BankAccount> accounts;

    private String name;
    private String address;
    private LocalDate dateOfBirth;

    // creditStatus is true if the customer has passed the credit check
    private boolean creditStatus;

    public Customer(String name, String address, LocalDate dateOfBirth) {
        this.accounts = new HashMap<>();

        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        creditCheck();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean getCreditStatus() {
        return creditStatus;
    }

    public void creditCheck() {
        // assume it always passes
        creditStatus = true;
    }

    public BankAccount getAccount(String accountNumber, String pin) {
        BankAccount account = accounts.get(accountNumber);

        if (account == null) {
            throw new RuntimeException("account not found");
        }

        if (!account.getPin().equals(pin)) {
            throw new RuntimeException("pin is incorrect");
        }

        return account;
    }

    public void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public void removeAccount(BankAccount account) {
        accounts.remove(account.getAccountNumber());
    }

    public int getAge() {
        LocalDate today = LocalDate.now();
        return today.getYear() - dateOfBirth.getYear();
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, has good credit: %b", name, address, dateOfBirth, creditStatus);
    }

    // this is for testing
    public HashMap<String, BankAccount> getAccounts() {
        return accounts;
    }
}
