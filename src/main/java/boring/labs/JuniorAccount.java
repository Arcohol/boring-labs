package boring.labs;

import java.time.LocalDate;

public class JuniorAccount extends BankAccount {
    // this is limit per day
    private double withdrawalLimit;

    // you have to record the last withdrawal date
    // if it's a new day, reset the withdrawal limit
    private LocalDate lastWithdrawalDate;

    protected JuniorAccount(String pin, Customer customer) {
        super(pin, customer);
        resetWithdrawalLimit();
    }

    private void resetWithdrawalLimit() {
        withdrawalLimit = 100.0;
    }

    @Override
    protected void addWithdrawal(double amount) {
        LocalDate today = LocalDate.now();

        // if it's a new day, or no withdrawal has been made
        // reset the withdrawal limit
        if (lastWithdrawalDate == null || !(lastWithdrawalDate.equals(today))) {
            resetWithdrawalLimit();
        }

        if (amount > withdrawalLimit) {
            throw new RuntimeException(
                    "your request is exceeding the withdrawal limit, which is " + withdrawalLimit);
        }

        if (amount > getBalance()) {
            throw new RuntimeException("Insufficient funds");
        }

        super.addWithdrawal(amount);
        withdrawalLimit -= amount;
        lastWithdrawalDate = today;
    }

    // inject the lastWithdrawalDate for testing
    public void setLastWithdrawalDate(LocalDate lastWithdrawalDate) {
        this.lastWithdrawalDate = lastWithdrawalDate;
    }
}
