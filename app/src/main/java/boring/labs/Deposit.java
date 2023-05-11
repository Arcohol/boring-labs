package boring.labs;

public class Deposit extends Transaction {
    private boolean isClear;

    public Deposit(double amount, TransactionType type) {
        super(amount);
        switch (type) {
            case CASH -> isClear = true;
            case CHEQUE -> isClear = false;
        }
    }

    public boolean isClear() {
        return isClear;
    }

    public void clear() {
        isClear = true;
    }
}
