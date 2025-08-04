package com.iut.fake;

import com.iut.Repository;
import com.iut.account.model.Account;
import java.util.*;

public class FakeAccountRepository implements Repository<Account, String> {

    private Map<String, Account> accounts = new HashMap<>();

    @Override
    public boolean save(Account input) {
        accounts.put(input.getId(), input);
        return true;
    }

    @Override
    public boolean update(Account input) {
        if (accounts.containsKey(input.getId())) {
            accounts.put(input.getId(), input);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return accounts.remove(id) != null;
    }

    @Override
    public boolean existsById(String id) {
        return accounts.containsKey(id);
    }

    @Override
    public Account findById(String id) {
        return accounts.get(id);
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }
}
