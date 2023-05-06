package boring.labs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestJuniorAccount {
    @Test
    void canWithdraw() {
        JuniorAccount ja = new JuniorAccount("2314");
        ja.deposit(100);
        ja.withdraw(50);
        assertEquals(50, ja.getBalance());
    }

    @Test
    void cannotExceedWithdrawalLimit() {
        JuniorAccount ja = new JuniorAccount("3982");
        ja.deposit(300);
        ja.withdraw(50);
        assertThrows(ExceedWithdrawalLimitException.class, () -> ja.withdraw(60));
    }

    @Test
    void cannotExceedBalance() {
        JuniorAccount ja = new JuniorAccount("9821");
        ja.deposit(50);
        assertThrows(InsufficientFundsException.class, () -> ja.withdraw(60));
    }
}
