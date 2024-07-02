package ru.job4j.di.model;

/**
 * @author dl
 * @date 02.07.2024 20:12
 */
public class NoContextDI {
    public static void main(String[] args) {
        Store store = new Store();
        StartUI ui = new StartUI(store);
        ui.add("Petr Arsentev");
        ui.add("Ivan ivanov");
        ui.print();
    }
}
