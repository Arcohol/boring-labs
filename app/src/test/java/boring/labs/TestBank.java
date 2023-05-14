package boring.labs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class TestBank {
    @Test
    void makeTransactions() {
        // say i have a bank
        Bank bank = new Bank();

        // i have added a customer
        Customer c = new Customer("Foo", "Bar", LocalDate.of(1989, 6, 4));
        bank.addCustomer(c);

        // i want to open an account for the customer
        String accno = bank.openAccount(c, "1234", AccountType.CURRENT);

        System.out.println("account number is " + accno);

        // i want to deposit money into the account
        // i will provide the account number and the amount
        bank.deposit(accno, 100, DepositType.CASH);

        // i want to check the balance
        assertEquals(100, bank.checkBalance(accno, "1234"));

        // the customer wants to withdraw money
        // i will provide the account number, the pin, and the amount
        assertThrowsExactly(RuntimeException.class, () -> bank.withdraw(accno, "1234", 1000));

        // now the customer can withdraw
        bank.withdraw(accno, "1234", 50);

        // i want to check the balance
        assertEquals(50, bank.checkBalance(accno, "1234"));

        // clear the balance so that i can close the account
        bank.withdraw(accno, "1234", 50);

        // i want to close the account
        bank.closeAccount(c, accno, "1234");
    }
}
