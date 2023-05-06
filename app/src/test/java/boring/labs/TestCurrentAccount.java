package boring.labs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestCurrentAccount {
    @Test
    void canOverdraft() {
        CurrentAccount ca = new CurrentAccount("2932");
        ca.deposit(100);
        ca.withdraw(200);
        assertEquals(-100, ca.getBalance());
    }

    @Test
    void cannotExceedOverdraftLimit() {
        CurrentAccount ca = new CurrentAccount("2932");
        ca.deposit(100);
        ca.withdraw(300);
        assertEquals(-200, ca.getBalance());
        ca.withdraw(200);
        assertEquals(-400, ca.getBalance());
        assertThrows(InsufficientFundsException.class, () -> ca.withdraw(200));
    }
}
