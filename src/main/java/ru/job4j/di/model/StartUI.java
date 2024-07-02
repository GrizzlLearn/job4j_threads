package ru.job4j.di.model;

/**
 * @author dl
 * @date 02.07.2024 20:10
 */
public class StartUI {
    private Store store;

    public StartUI(Store store) {
        this.store = store;
    }

    public void add(String value) {
        store.add(value);
    }

    public void print() {
        store.getAll().forEach(System.out::println);
    }
}
