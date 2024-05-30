package ru.job4j.cash;

/**
 * @author dl
 * @date 24.05.2024 23:44
 */
public record Account(int id, int amount) {
    public synchronized Account changeAmount(boolean action, int additionalAmount) {
        return action
                ? new Account(this.id, this.amount + additionalAmount)
                : new Account(this.id, this.amount - additionalAmount);
    }
}
