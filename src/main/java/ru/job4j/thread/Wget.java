package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author dl
 * @date 11.04.2024 23:04
 */
public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String dirName;
    private final String fileName;

    public Wget(String url, int speed, String dirName, String fileName) {
        this.url = url;
        this.speed = speed;
        this.dirName = dirName;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        long startAt = System.currentTimeMillis();
        int second = 1000;
        File file = new File(String.format("%s/%s", dirName, fileName));
        byte[] dataBuffer = new byte[speed];
        long timeSpent = 0;
        try (InputStream input = new URL(url).openStream();
             FileOutputStream output = new FileOutputStream(file)) {
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                timeSpent = System.currentTimeMillis() - startAt;
                if (bytesRead == speed && timeSpent < second) {
                    System.out.println("time spent: " + timeSpent);
                    Thread.sleep(second - timeSpent);
                    timeSpent = 0;
                }
                output.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Read 512 bytes : " + timeSpent + " nano.");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WgetValidator validator = new WgetValidator(args);

        if (validator.isValid()) {
            Thread wget = new Thread(new Wget(args[0], Integer.parseInt(args[1]), args[2], args[3]));
            wget.start();
            wget.join();
        }
    }
}
