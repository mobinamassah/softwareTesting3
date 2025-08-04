package com.iut.account.service;

import com.iut.Repository;
import com.iut.account.model.Account;
import java.util.List;
import java.util.stream.Collectors;

public class AccountService {

    private final Repository<Account, String> repository;

    public AccountService(Repository<Account, String> repository) {
        this.repository = repository;
    }

    public Repository<Account, String> getRepository() {
        return repository;
    }

    public boolean createAccount(String id, int initialBalance, String userId) {
        if (repository.existsById(id)) {
            return false;
        }
        Account account = new Account(id, initialBalance);
        account.setUserId(userId);
        return repository.save(account);
    }

    public boolean deposit(String accountId, int amount) {
        if (repository.existsById(accountId)) {
            Account account = repository.findById(accountId);
            account.setBalance(account.getBalance() + amount);
            return repository.update(account);
        }
        return false;
    }

    public boolean withdraw(String accountId, int amount) {
        if (repository.existsById(accountId)) {
            Account account = repository.findById(accountId);
            if (account.getBalance() < amount) {
                throw new IllegalArgumentException("Insufficient funds");
            }
            account.setBalance(account.getBalance() - amount);
            return repository.update(account);
        }
        return false;
    }

    public boolean transfer(String fromId, String toId, int amount) {
        if (repository.existsById(fromId) && repository.existsById(toId)) {
            Account from = repository.findById(fromId);
            Account to = repository.findById(toId);
            if (from.getBalance() < amount) {
                throw new IllegalArgumentException("Insufficient funds in source account");
            }
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);
            repository.update(from);
            repository.update(to);
            return true;
        }
        return false;
    }

    public int getBalance(String id) {
        if (repository.existsById(id)) {
            return repository.findById(id).getBalance();
        }
        throw new IllegalArgumentException("Account not found");
    }

    public boolean deleteAccount(final String id) {
        return repository.delete(id);
    }

    public Account getAccount(String id) {
        return repository.findById(id);
    }

    public List<Account> getAccountsByUserId(String userId) {
        return repository.findAll()
                .stream()
                .filter(account -> account.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
