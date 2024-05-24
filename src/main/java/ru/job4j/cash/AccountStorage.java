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

    public boolean add(Account account) {
        return false;
    }

    public boolean update(Account account) {
        return false;
    }

    public void delete(int id) {

    }

    public Optional<Account> getById(int id) {
        return Optional.empty();
    }

    public boolean transfer(int fromId, int toId, int amount) {
        return false;
    }
}
