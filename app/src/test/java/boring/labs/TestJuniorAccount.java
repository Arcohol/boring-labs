package boring.labs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestJuniorAccount {
    @Test
    void canWithdraw() {
        JuniorAccount ja = new JuniorAccount("12039104", "John");
        ja.deposit(100);
        ja.withdraw(50);
        assertEquals(50, ja.getBalance());
    }

    @Test
    void cannotExceedWithdrawalLimit() {
        JuniorAccount ja = new JuniorAccount("12039104", "John");
        ja.deposit(300);
        ja.withdraw(50);
        assertThrows(ExceedWithdrawalLimitException.class, () -> ja.withdraw(60));
    }

    @Test
    void cannotExceedBalance() {
        JuniorAccount ja = new JuniorAccount("12039104", "John");
        ja.deposit(50);
        assertThrows(InsufficientFundsException.class, () -> ja.withdraw(60));
    }
}
