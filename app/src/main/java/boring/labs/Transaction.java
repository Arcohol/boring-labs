package boring.labs;

import java.time.LocalDateTime;

public abstract class Transaction {
    private LocalDateTime date;
    private double amount;

    public Transaction(double amount) {
        this.date = LocalDateTime.now();
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f", date, amount);
    }
}
