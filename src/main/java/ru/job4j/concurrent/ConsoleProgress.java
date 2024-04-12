package ru.job4j.concurrent;

/**
 * @author dl
 * @date 09.04.2024 23:30
 */
public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ConsoleProgress());
        thread.run();
    }

    @Override
    public void run() {
        int count = 0;
        char[] process = new char[] {'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(240);
                count = count < process.length ? count : 0;
                System.out.print("\r load: " + process[count++]);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
