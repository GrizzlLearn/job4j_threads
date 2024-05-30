package ru.job4j.cash;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author dl
 * @date 24.05.2024 23:46
 */
class AccountStorageTest {

    @Test
    void whenAdd() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
    }

    @Test
    void whenAddWithNegativeAmount() {
        var storage = new AccountStorage();
        String errorText = "Amount cannot be negative";
        assertThatThrownBy(() -> storage.add(new Account(1, -100)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(errorText);
    }

    @Test
    void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenDeleteNegativeOrZero() {
        var storage = new AccountStorage();
        String errorText = "Id cannot be negative";
        storage.add(new Account(1, 100));
        assertThatThrownBy(() -> storage.delete(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(errorText);
        assertThatThrownBy(() -> storage.delete(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(errorText);
    }

    @Test
    void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenTransferZeroOrNegativeAmount() {
        var storage = new AccountStorage();
        String errorText = "Amount cannot be zero or negative";
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        assertThatThrownBy(() -> storage.transfer(1, 2, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(errorText);

        assertThatThrownBy(() -> storage.transfer(1, 2, -100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(errorText);
    }
}
