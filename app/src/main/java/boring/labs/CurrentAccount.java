package boring.labs;

public class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountName) {
        super(accountNumber, accountName);
        overdraftLimit = 500.0;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > (getBalance() + getOverdraftLimit())) {
            throw new InsufficientFundsException("overdraft limit exceeded");
        }

        super.withdraw(amount);
    }
}
