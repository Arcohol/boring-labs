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

    private void resetWithdrawalLimit() {
        withdrawalLimit = 100.0;
    }

    private void setLastWithdrawalDate(LocalDate lastWithdrawalDate) {
        this.lastWithdrawalDate = lastWithdrawalDate;
    }

    @Override
    public void withdraw(double amount) {
        // if it's a new day, reset the withdrawal limit
        LocalDate today = LocalDate.now();
        if (lastWithdrawalDate == null || !lastWithdrawalDate.equals(today)) {
            resetWithdrawalLimit();
            setLastWithdrawalDate(today);
        }

        if (amount > getWithdrawalLimit()) {
            throw new ExceedWithdrawalLimitException(
                    "your request is exceeding the withdrawal limit, which is " + withdrawalLimit);
        }

        if (amount > getBalance()) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        super.withdraw(amount);
        withdrawalLimit -= amount;
    }
}
