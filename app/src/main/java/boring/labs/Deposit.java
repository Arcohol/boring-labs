package boring.labs;

public class Deposit extends Transaction {
    private boolean isClear;

    public Deposit(double amount) {
        super(amount);
        this.isClear = false;
    }

    public boolean isClear() {
        return isClear;
    }

    public void clear() {
        isClear = true;
    }
}
