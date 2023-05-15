package boring.labs;

import java.time.LocalDate;

public class SaverAccount extends BankAccount {
    record Notice(LocalDate date, double amount) {
    }

    Notice notice;

    public SaverAccount(String pin, Customer customer) {
        super(pin, customer);
    }

    // Notice must be set before any withdrawal
    public void setNotice(LocalDate date, double amount) {
        notice = new Notice(date, amount);
    }

    @Override
    public void addWithdrawal(double amount) {
        if (notice == null) {
            throw new RuntimeException("notice is not set");
        }

        // notice must be given 3 days before withdrawal
        if (notice.date().isAfter(LocalDate.now().minusDays(3))) {
            throw new RuntimeException("notice period is not met");
        }

        if (amount > getBalance()) {
            throw new RuntimeException("Insufficient funds");
        }

        if (amount > notice.amount()) {
            throw new RuntimeException("withdrawal amount exceeds notice amount");
        }

        super.addWithdrawal(amount);

        // assume that after one withdrawal, the notice is cleared
        // and you have to set it again
        notice = null;
    }
}
