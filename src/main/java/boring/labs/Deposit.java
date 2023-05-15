package boring.labs;

public class Deposit extends Transaction {
    private boolean isClear;
    private DepositType type;

    protected Deposit(double amount, DepositType type) {
        super(amount);
        this.type = type;
        switch (type) {
            case CASH -> isClear = true;
            case CHEQUE -> isClear = false;
        }
    }

    protected DepositType getType() {
        return type;
    }

    protected boolean isClear() {
        return isClear;
    }

    protected void clear() {
        isClear = true;
    }
}
