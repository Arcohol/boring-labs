package boring.labs;

import java.util.HashMap;

/**
 * Bank is a HashMap of BankAccounts. It maps account numbers to BankAccounts. It has methods to
 * open and close accounts, deposit and withdraw money, and check balances.
 */
public class Bank extends HashMap<String, BankAccount> {
    public Bank() {
        super();
    }

    public void openAccount() {}

    public void closeAccount() {}

    public void deposit() {}

    public void withdraw() {}

    public void checkBalance() {}
}
