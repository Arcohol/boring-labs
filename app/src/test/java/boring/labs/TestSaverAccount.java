package boring.labs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class TestSaverAccount {
    Customer c = new Customer("Foo", "Bar", LocalDate.of(1989, 6, 4));

    @Test
    void canWithdraw() {
        SaverAccount sa = new SaverAccount("2314", c);
        sa.addDeposit(100);
        sa.clearFunds();
        sa.setNotice(LocalDate.now().minusDays(3), 50);
        sa.addWithdrawal(50);
        assertEquals(50, sa.getBalance());
    }

    @Test
    void cannotWithdrawWithoutNotice() {
        SaverAccount sa = new SaverAccount("3982", c);
        sa.addDeposit(300);
        sa.clearFunds();

        assertThrows(RuntimeException.class, () -> sa.addWithdrawal(60));

        sa.setNotice(LocalDate.now().minusDays(3), 50);

        assertThrows(RuntimeException.class, () -> sa.addWithdrawal(60));

        sa.addWithdrawal(30);

        assertEquals(270, sa.getBalance());
    }
}
