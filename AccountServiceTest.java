package com.iut;

import com.iut.account.model.Account;
import com.iut.account.service.AccountService;
import com.iut.fake.FakeAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    private Repository<Account, String> repository;
    private AccountService accountService;

    @BeforeEach
    void setup() {
        repository = new FakeAccountRepository();
        accountService = new AccountService(repository);
    }

    @Test
    void createAccountTest() {
        boolean result = accountService.createAccount("A1", 1000, "U1");
        assertTrue(result);
        assertTrue(repository.existsById("A1"));

        boolean duplicate = accountService.createAccount("A1", 500, "U1");
        assertFalse(duplicate);
    }

    @Test
    void depositTest() {
        accountService.createAccount("A2", 500, "U2");
        boolean result = accountService.deposit("A2", 200);
        assertTrue(result);
        assertEquals(700, accountService.getBalance("A2"));

        assertFalse(accountService.deposit("AX", 100));
    }

    @Test
    void withdrawTest() {
        accountService.createAccount("A3", 800, "U3");
        boolean result = accountService.withdraw("A3", 300);
        assertTrue(result);
        assertEquals(500, accountService.getBalance("A3"));

        assertThrows(IllegalArgumentException.class, () -> accountService.withdraw("A3", 600));

        assertFalse(accountService.withdraw("AX", 100));
    }

    @Test
    void transferTest() {
        accountService.createAccount("A4", 1000, "U4");
        accountService.createAccount("A5", 500, "U5");

        boolean result = accountService.transfer("A4", "A5", 400);
        assertTrue(result);
        assertEquals(600, accountService.getBalance("A4"));
        assertEquals(900, accountService.getBalance("A5"));

        assertThrows(IllegalArgumentException.class, () -> accountService.transfer("A4", "A5", 1000));

        assertFalse(accountService.transfer("AX", "A5", 100));
    }

    @Test
    void getBalanceTest() {
        accountService.createAccount("A6", 1200, "U6");
        assertEquals(1200, accountService.getBalance("A6"));

        assertThrows(IllegalArgumentException.class, () -> accountService.getBalance("AX"));
    }

    @Test
    void existsAndGetAccountTest() {
        accountService.createAccount("A7", 400, "U7");
        assertTrue(repository.existsById("A7"));

        Account account = accountService.getAccount("A7");
        assertNotNull(account);
        assertEquals("A7", account.getId());
        assertEquals(400, account.getBalance());
    }

    @Test
    void getAllAccountsTest() {
        accountService.createAccount("A8", 200, "U8");
        accountService.createAccount("A9", 300, "U9");

        assertEquals(2, repository.findAll().size());
    }
}
