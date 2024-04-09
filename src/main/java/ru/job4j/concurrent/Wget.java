package ru.job4j.concurrent;

/**
 * @author dl
 * @date 09.04.2024 22:52
 */
public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    for (int i = 0; i <=100; i++) {
                        try {
                            Thread.sleep(1000);
                            System.out.print("\rLoading : " + i + "%");
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
        thread.start();
    }
}
