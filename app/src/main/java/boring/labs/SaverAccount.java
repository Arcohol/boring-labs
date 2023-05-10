package boring.labs;

import java.time.LocalDate;

public class SaverAccount extends BankAccount {
    record Notice(LocalDate date, double amount) {
    }

    Notice notice;

    public SaverAccount(String pin, Customer customer) {
        super(pin, customer);
    }

    public void setNotice(LocalDate date, double amount) {
        notice = new Notice(date, amount);
    }

    @Override
    public void addWithdrawal(double amount) {
        if (notice == null) {
            throw new RuntimeException("notice is not set");
        }

        if (notice.date().isAfter(LocalDate.now())) {
            throw new RuntimeException("notice period not passed");
        }

        if (amount > getBalance()) {
            throw new RuntimeException("Insufficient funds");
        }

        if (amount > notice.amount()) {
            throw new RuntimeException("withdrawal amount exceeds notice amount");
        }

        super.addWithdrawal(amount);
    }
}
