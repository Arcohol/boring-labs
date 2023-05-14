package boring.labs;

public class Deposit extends Transaction {
    private boolean isClear;
    private DepositType type;

    public Deposit(double amount, DepositType type) {
        super(amount);
        this.type = type;
        switch (type) {
            case CASH -> isClear = true;
            case CHEQUE -> isClear = false;
        }
    }

    public DepositType getType() {
        return type;
    }

    public boolean isClear() {
        return isClear;
    }

    public void clear() {
        isClear = true;
    }
}
