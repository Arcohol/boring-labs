package boring.labs;

// CurrentAccount can be overdrawn up to a limit
// The overdraft limit is set to 500.0 by default
// It has a different addWithdrawal method
public class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    protected CurrentAccount(String pin, Customer customer) {
        super(pin, customer);
        this.overdraftLimit = 500.0;
    }

    @Override
    protected void addWithdrawal(double amount) {
        if (amount > (getBalance() + overdraftLimit)) {
            throw new RuntimeException("overdraft limit exceeded");
        }

        super.addWithdrawal(amount);
    }
}
