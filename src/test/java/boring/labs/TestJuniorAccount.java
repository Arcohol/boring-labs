package boring.labs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class TestJuniorAccount {
    Customer c = new Customer("Foo", "Bar", LocalDate.of(1989, 6, 4));

    @Test
    void canWithdraw() {
        JuniorAccount ja = new JuniorAccount("2314", c);
        ja.addDeposit(100, DepositType.CHEQUE);
        ja.clearFunds();
        ja.addWithdrawal(50);
        assertEquals(50, ja.getBalance());
    }

    @Test
    void cannotExceedWithdrawalLimit() {
        JuniorAccount ja = new JuniorAccount("3982", c);
        ja.addDeposit(300, DepositType.CHEQUE);
        ja.clearFunds();
        ja.addWithdrawal(50);
        assertThrows(RuntimeException.class, () -> ja.addWithdrawal(60));
    }

    @Test
    void cannotExceedBalance() {
        JuniorAccount ja = new JuniorAccount("9821", c);
        ja.addDeposit(50, DepositType.CHEQUE);
        ja.clearFunds();
        assertThrows(RuntimeException.class, () -> ja.addWithdrawal(60));
    }
}
