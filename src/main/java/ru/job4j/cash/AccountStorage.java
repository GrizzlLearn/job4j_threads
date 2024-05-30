package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author dl
 * @date 24.05.2024 23:45
 */

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        if (account.amount() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        return this.accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        boolean result = false;
        if (getById(account.id()).isPresent()) {
            this.accounts.replace(account.id(), account);
            result = true;
        }
        return result;
    }

    public synchronized void delete(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Id cannot be negative");
        }

        Optional<Account> tmp = getById(id);
        tmp.ifPresent(account -> accounts.remove(account.id()));
    }

    public synchronized Optional<Account> getById(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Id cannot be negative");
        }

        return accounts.containsKey(id)
                ? Optional.of(accounts.get(id))
                : Optional.empty();
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (fromId < 1 || toId < 1) {
            throw new IllegalArgumentException("Id cannot be negative");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be zero or negative");
        }

        boolean result = false;
        if (getById(fromId).isPresent()
                && getById(toId).isPresent()
                && accounts.get(fromId).amount() >= amount) {
                    accounts.put(fromId, accounts.get(fromId).changeAmount(false, amount));
                    accounts.put(toId, accounts.get(toId).changeAmount(true, amount));
                    result = true;
        }
        return result;
    }
}
