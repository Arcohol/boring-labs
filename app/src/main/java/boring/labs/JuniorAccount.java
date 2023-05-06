package boring.labs;

import java.time.LocalDate;

public class JuniorAccount extends BankAccount {
    // this is limit per day
    private double withdrawalLimit;
    // have to record the last withdrawal date
    // if it's a new day, reset the withdrawal limit
    private LocalDate lastWithdrawalDate;

    public JuniorAccount(String accountNumber, String accountName) {
        super(accountNumber, accountName);
        this.withdrawalLimit = 100.0;
    }

    public double getWithdrawalLimit() {
        return withdrawalLimit;
    }

    @Override
    public void withdraw(double amount) {
        LocalDate today = LocalDate.now();
        if (lastWithdrawalDate == null) {
            lastWithdrawalDate = today;
        } else if (!lastWithdrawalDate.equals(today)) {
            lastWithdrawalDate = today;
            withdrawalLimit = 100.0;
        }

        if (amount > withdrawalLimit) {
            throw new IllegalArgumentException("Withdrawal limit exceeded");
        }

        if (amount > getBalance()) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        super.withdraw(amount);
        withdrawalLimit -= amount;
    }
}
