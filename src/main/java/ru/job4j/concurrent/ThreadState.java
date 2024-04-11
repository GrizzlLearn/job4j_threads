package ru.job4j.concurrent;

/**
 * @author dl
 * @date 09.04.2024 20:19
 */
public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {  }
        );
        System.out.println(first.getState());
        System.out.println(first.getName());
        Thread second = new Thread(
                () -> {  }
        );
        System.out.println(second.getState());
        System.out.println(second.getName());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("first: " + first.getState());
            System.out.println("second: " + second.getState());
        }
        System.out.println("work completed");
    }
}
