package boring.labs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class TestBank {
    @Test
    void openAccount() {
        Bank bank = new Bank();

        Customer c = new Customer("Foo", "Bar", LocalDate.of(2010, 6, 4));
        bank.addCustomer(c);

        bank.openAccount(c, "123456", AccountType.CURRENT);
        bank.openAccount(c, "323232", AccountType.JUNIOR);
        bank.openAccount(c, "498123", AccountType.SAVER);

        // you can open accounts for different customers
        Customer c2 = new Customer("Baz", "Qux", LocalDate.of(1990, 7, 5));
        bank.addCustomer(c2);

        // you can't open a junior account unless the customer is under 16
        assertThrows(RuntimeException.class,
                () -> bank.openAccount(c2, "123123", AccountType.JUNIOR));
        // you can still open other accounts for the customer
        bank.openAccount(c2, "123123", AccountType.CURRENT);

        // bank has a total of 4 accounts
        assertEquals(4, bank.getAccounts().size());

        // and 2 customers
        assertEquals(2, bank.getCustomers().size());

        // customer c has 3 accounts
        assertEquals(3, c.getAccounts().size());

        // customer c2 has 1 account

        for (BankAccount acc : bank.getAccounts().values()) {
            System.out.println(acc);
        }

        for (Customer cust : bank.getCustomers()) {
            System.out.println(cust);
        }
    }

    @Test
    void depositFunds() {
        Bank bank = new Bank();

        Customer c = new Customer("Foo", "Bar", LocalDate.of(2010, 6, 4));
        bank.addCustomer(c);

        // create 3 different accounts
        String acc1 = bank.openAccount(c, "123456", AccountType.CURRENT);
        String acc2 = bank.openAccount(c, "323232", AccountType.JUNIOR);
        String acc3 = bank.openAccount(c, "498123", AccountType.SAVER);

        // each of them has the same deposit policy
        // i.e. you can deposit cash or cheque
        // if you deposit cash, the funds are available immediately
        // if you deposit cheque, the funds are available after clearing
        // the funds are cleared by calling clearFunds()
        // after clearing, the funds are available immediately

        // provided the pin is correct, you can check the balance

        // deposit 100 cash to acc1
        bank.deposit(acc1, 100, DepositType.CASH);
        assertEquals(100, bank.checkBalance(acc1, "123456"));

        // deposit 100 cheque to acc2
        bank.deposit(acc2, 100, DepositType.CHEQUE);

        // funds are not available yet
        assertEquals(0, bank.checkBalance(acc2, "323232"));

        // clear funds
        bank.clearFunds();

        // funds are available now
        assertEquals(100, bank.checkBalance(acc2, "323232"));

        // try this again
        bank.deposit(acc3, 200, DepositType.CHEQUE);
        bank.deposit(acc3, 100, DepositType.CASH);
        assertEquals(100, bank.checkBalance(acc3, "498123"));
        bank.clearFunds();
        assertEquals(300, bank.checkBalance(acc3, "498123"));

        // you can't deposit to an account that doesn't exist
        assertThrows(RuntimeException.class, () -> bank.deposit("123123", 100, DepositType.CASH));

        // you can't deposit to an account that is closed
        bank.withdraw(acc1, "123456", 100);
        bank.closeAccount(c, acc1, "123456");
        assertThrows(RuntimeException.class, () -> bank.deposit(acc1, 100, DepositType.CASH));

        // you can't deposit to an account that is suspended
        bank.suspendAccount(c, acc2, "323232");
        assertThrows(RuntimeException.class, () -> bank.deposit(acc2, 100, DepositType.CASH));


        for (BankAccount acc : bank.getAccounts().values()) {
            System.out.println(acc);
        }

        for (Customer cust : bank.getCustomers()) {
            System.out.println(cust);
        }
    }

    @Test
    void clearFunds() {
        // this is tested in the deposit() test
        // but we can test it again here
        Bank bank = new Bank();

        Customer c = new Customer("Foo", "Bar", LocalDate.of(2010, 6, 4));
        bank.addCustomer(c);

        String acc1 = bank.openAccount(c, "123456", AccountType.CURRENT);

        bank.deposit(acc1, 100, DepositType.CASH);
        assertEquals(100, bank.checkBalance(acc1, "123456"));

        bank.clearFunds();
        assertEquals(100, bank.checkBalance(acc1, "123456"));

        bank.deposit(acc1, 100, DepositType.CHEQUE);
        assertEquals(100, bank.checkBalance(acc1, "123456"));

        bank.clearFunds();
        assertEquals(200, bank.checkBalance(acc1, "123456"));
    }

    @Test
    void withdrawFunds() {
        // different types of accounts have different withdrawal policies
        // current account: you can withdraw any amount, but you cannot exceed the overdraft limit
        // junior account: you can withdraw up to the daily withdrawal limit, and you cannot exceed
        // the balance
        // saver account: you can withdraw any amount, but you have to give notice, also you cannot
        // exceed the balance

        // in this test, we deposit cash for simplicity

        Bank bank = new Bank();

        Customer c = new Customer("Foo", "Bar", LocalDate.of(2010, 6, 4));
        bank.addCustomer(c);

        String acc1 = bank.openAccount(c, "123456", AccountType.CURRENT);
        String acc2 = bank.openAccount(c, "323232", AccountType.JUNIOR);
        String acc3 = bank.openAccount(c, "498123", AccountType.SAVER);

        // withdraw 100 from current account
        bank.deposit(acc1, 100, DepositType.CASH);
        bank.withdraw(acc1, "123456", 100);
        assertEquals(0, bank.checkBalance(acc1, "123456"));

        // try to withdraw more than the balance
        bank.deposit(acc1, 100, DepositType.CASH);
        bank.withdraw(acc1, "123456", 200);
        assertEquals(-100, bank.checkBalance(acc1, "123456"));

        // but you cannot exceed the overdraft limit
        // by default, the limit is 500
        bank.withdraw(acc1, "123456", 400);
        assertEquals(-500, bank.checkBalance(acc1, "123456"));
        assertThrows(RuntimeException.class, () -> bank.withdraw(acc1, "123456", 100));
        bank.deposit(acc1, 100, DepositType.CASH);
        bank.withdraw(acc1, "123456", 50);
        assertEquals(-450, bank.checkBalance(acc1, "123456"));

        // withdraw 50 from junior account
        // junior account has a daily withdrawal limit of 100
        bank.deposit(acc2, 200, DepositType.CASH);
        bank.withdraw(acc2, "323232", 50);
        assertEquals(150, bank.checkBalance(acc2, "323232"));
        bank.withdraw(acc2, "323232", 50);
        assertEquals(100, bank.checkBalance(acc2, "323232"));
        // you cannot exceed the daily withdrawal limit
        assertThrows(RuntimeException.class, () -> bank.withdraw(acc2, "323232", 50));

        // mock the date
        // pretend withdrawal was made yesterday
        // so that you can withdraw again today
        JuniorAccount ja = (JuniorAccount) bank.getAccounts().get(acc2);
        ja.setLastWithdrawalDate(LocalDate.now().minusDays(1));
        bank.withdraw(acc2, "323232", 50);
        assertEquals(50, bank.checkBalance(acc2, "323232"));

        // you cannot exceed the balance
        assertThrows(RuntimeException.class, () -> bank.withdraw(acc2, "323232", 100));

        // withdraw 50 from saver account
        // saver account requires notice
        // if you don't give notice, you cannot withdraw
        bank.deposit(acc3, 100, DepositType.CASH);
        assertThrows(RuntimeException.class, () -> bank.withdraw(acc3, "498123", 50));
        // give notice
        // say, you notice the bank 3 days ago
        bank.giveNotice(acc3, "498123", 50, LocalDate.now().minusDays(3));
        bank.withdraw(acc3, "498123", 50);
        assertEquals(50, bank.checkBalance(acc3, "498123"));

        // you cannot exceed notice amount, even if you have enough balance
        bank.deposit(acc3, 500, DepositType.CASH);
        bank.giveNotice(acc3, "498123", 200, LocalDate.now().minusDays(3));
        assertThrows(RuntimeException.class, () -> bank.withdraw(acc3, "498123", 300));

        // you cannot exceed the balance
        bank.giveNotice(acc3, "498123", 1000, LocalDate.now().minusDays(3));
        assertThrows(RuntimeException.class, () -> bank.withdraw(acc3, "498123", 880));

        // you cannot withdraw if notice period is not yet passed
        // say, you notice the bank 2 days ago
        bank.giveNotice(acc3, "498123", 100, LocalDate.now().minusDays(2));
        assertThrows(RuntimeException.class, () -> bank.withdraw(acc3, "498123", 100));
    }

    @Test
    void suspendAccount() {
        // if you suspend an account, you cannot neither deposit nor withdraw
        // but you can still check the balance
        Bank bank = new Bank();

        Customer c = new Customer("Foo", "Bar", LocalDate.of(2010, 6, 4));
        bank.addCustomer(c);

        String acc = bank.openAccount(c, "123456", AccountType.CURRENT);

        bank.deposit(acc, 100, DepositType.CASH);
        bank.suspendAccount(c, acc, "123456");
        assertThrows(RuntimeException.class, () -> bank.deposit(acc, 100, DepositType.CASH));
        assertThrows(RuntimeException.class, () -> bank.withdraw(acc, "123456", 100));
        assertEquals(100, bank.checkBalance(acc, "123456"));

        // you cannot suspend an account that doesn't exist
        assertThrows(RuntimeException.class, () -> bank.suspendAccount(c, "123123", "123456"));

        // you can reinstate a suspended account
        bank.reinstateAccount(c, acc, "123456");
        bank.deposit(acc, 100, DepositType.CASH);
        bank.withdraw(acc, "123456", 100);
        assertEquals(100, bank.checkBalance(acc, "123456"));
    }

    @Test
    void closeAccount() {
        // if you close an account, you cannot deposit, withdraw
        // and the balance must be 0
        Bank bank = new Bank();

        Customer c = new Customer("Foo", "Bar", LocalDate.of(2010, 6, 4));
        bank.addCustomer(c);

        String acc = bank.openAccount(c, "123456", AccountType.CURRENT);

        bank.deposit(acc, 100, DepositType.CASH);
        assertThrows(RuntimeException.class, () -> bank.closeAccount(c, acc, "123456"));
        bank.withdraw(acc, "123456", 100);
        bank.closeAccount(c, acc, "123456");

        assertThrows(RuntimeException.class, () -> bank.deposit(acc, 100, DepositType.CASH));
        assertThrows(RuntimeException.class, () -> bank.withdraw(acc, "123456", 100));
        assertEquals(0, bank.checkBalance(acc, "123456"));

        // you cannot close an account that doesn't exist
        assertThrows(RuntimeException.class, () -> bank.closeAccount(c, "123123", "123456"));
    }
}
