package boring.labs;

import java.time.LocalDate;

public class JuniorAccount extends BankAccount {
    // this is limit per day
    private double withdrawalLimit;
    // have to record the last withdrawal date
    // if it's a new day, reset the withdrawal limit
    private LocalDate lastWithdrawalDate;

    public JuniorAccount(String pin, Customer customer) {
        super(pin, customer);
        resetWithdrawalLimit();
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
    public void addWithdrawal(double amount) {
        // if it's a new day, reset the withdrawal limit
        LocalDate today = LocalDate.now();
        if (lastWithdrawalDate == null || !lastWithdrawalDate.equals(today)) {
            resetWithdrawalLimit();
            setLastWithdrawalDate(today);
        }

        if (amount > getWithdrawalLimit()) {
            throw new RuntimeException(
                    "your request is exceeding the withdrawal limit, which is " + withdrawalLimit);
        }

        if (amount > getBalance()) {
            throw new RuntimeException("Insufficient funds");
        }

        super.addWithdrawal(amount);
        withdrawalLimit -= amount;
    }
}
