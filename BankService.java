package com.iut;

import com.iut.account.model.Account;
import com.iut.account.repo.AccountRepository;
import com.iut.account.service.AccountService;
import com.iut.user.model.User;
import com.iut.user.service.UserService;

import java.util.List;
import java.util.UUID;

public class BankService {
    private final UserService userService;
    private final AccountService accountService;

    public BankService(final UserService userService, final AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public boolean registerNewUser(User user) {
        if (!userService.createUser(user)) {
            return false;
        }
        String defaultAccountId = UUID.randomUUID().toString();
        accountService.createAccount(defaultAccountId, 0, user.getId());
        return true;
    }

    public List<Account> getUserAccounts(String userId) {
        return ((AccountRepository) accountService.getRepository()).findByUserId(userId);
    }

    public boolean addAccountToUser(String userId, Account account) {
        User user = userService.getUser(userId);
        if (user == null) return false;
        return accountService.createAccount(account.getId(), account.getBalance(), userId);
    }

    public User getUser(String userId) {
        return userService.getUser(userId);
    }

    public Account getAccount(String accountId) {
        return accountService.getAccount(accountId);
    }

    public boolean deleteAccount(String accountId) {
        return accountService.deleteAccount(accountId);
    }
}
