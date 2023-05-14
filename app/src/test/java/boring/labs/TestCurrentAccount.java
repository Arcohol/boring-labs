package boring.labs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class TestCurrentAccount {
    Customer c = new Customer("Foo", "Bar", LocalDate.of(1989, 6, 4));

    @Test
    void canOverdraft() {
        CurrentAccount ca = new CurrentAccount("2932", c);
        ca.addDeposit(100, DepositType.CHEQUE);
        ca.clearFunds();

        ca.addWithdrawal(200);
        assertEquals(-100, ca.getBalance());
    }

    @Test
    void cannotExceedOverdraftLimit() {
        CurrentAccount ca = new CurrentAccount("2932", c);
        ca.addDeposit(100, DepositType.CHEQUE);
        ca.clearFunds();

        ca.addWithdrawal(300);
        assertEquals(-200, ca.getBalance());
        ca.addWithdrawal(200);
        assertEquals(-400, ca.getBalance());
        assertThrows(RuntimeException.class, () -> ca.addWithdrawal(200));
    }
}
