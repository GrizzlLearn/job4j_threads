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
        return this.accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return getById(account.id())
                .map(existingAccount -> this.accounts.replace(account.id(), account) != null)
                .orElse(false);
    }

    public synchronized void delete(int id) {
        getById(id).ifPresent(account -> this.accounts.remove(account.id()));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(this.accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        Optional<Account> fromIdAcc = getById(fromId);
        Optional<Account> toIdAcc = getById(toId);
        if (fromIdAcc.isPresent()
                && toIdAcc.isPresent()
                && accounts.get(fromId).amount() >= amount) {
                    this.accounts.put(fromIdAcc.get().id(), new Account(fromIdAcc.get().id(), fromIdAcc.get().amount() - amount));
                    this.accounts.put(toIdAcc.get().id(), new Account(toIdAcc.get().id(), toIdAcc.get().amount() + amount));
                    result = true;
        }
        return result;
    }
}
