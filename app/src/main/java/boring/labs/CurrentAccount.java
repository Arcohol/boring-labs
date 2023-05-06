package boring.labs;

public class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountName) {
        super(accountNumber, accountName);
        this.overdraftLimit = 500.0;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > (getBalance() + overdraftLimit)) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        super.withdraw(amount);
    }
}
