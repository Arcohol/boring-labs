package boring.labs;

import java.time.LocalDate;
import java.util.LinkedList;

public class Customer {
    private LinkedList<BankAccount> accounts;

    private String name;
    private String address;
    private LocalDate dateOfBirth;

    // creditStatus is true if the customer has passed the credit check
    private boolean creditStatus;

    public Customer(String name, String address, LocalDate dateOfBirth) {
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
        // simulate credit check
        creditStatus = true;
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }
}
