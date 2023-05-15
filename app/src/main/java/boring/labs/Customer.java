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

    protected Customer(String name, String address, LocalDate dateOfBirth) {
        this.accounts = new HashMap<>();

        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        creditCheck();
    }

    protected String getName() {
        return name;
    }

    protected String getAddress() {
        return address;
    }

    protected LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    protected boolean getCreditStatus() {
        return creditStatus;
    }

    protected void creditCheck() {
        // assume it always passes
        creditStatus = true;
    }

    protected BankAccount getAccount(String accountNumber, String pin) {
        BankAccount account = accounts.get(accountNumber);

        if (account == null) {
            throw new RuntimeException("account not found");
        }

        if (!account.getPin().equals(pin)) {
            throw new RuntimeException("pin is incorrect");
        }

        return account;
    }

    protected void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    protected void removeAccount(BankAccount account) {
        accounts.remove(account.getAccountNumber());
    }

    protected int getAge() {
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
