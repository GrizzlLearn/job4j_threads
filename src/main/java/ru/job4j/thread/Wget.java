package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        int dataBufferSize = 512;
        File file = new File(String.format("%s/%s", dirName, fileName));

        if (!file.exists()) {
            long timeSpent = 0;
            try (InputStream input = new URL(url).openStream();
                 FileOutputStream output = new FileOutputStream(file)) {
                Thread.sleep((dataBufferSize / speed) - timeSpent);
                System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
                byte[] dataBuffer = new byte[dataBufferSize];
                int bytesRead;
                while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                    long downloadAt = System.nanoTime();
                    output.write(dataBuffer, 0, bytesRead);
                    timeSpent = System.nanoTime() - downloadAt;
                    System.out.println("Read 512 bytes : " + timeSpent + " nano.");
                }
            } catch (IOException | InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String dirName = args[2];
        String fileName = args[3];
        if (validateUrl(url) && validateFileName(dirName, fileName)) {
            Thread wget = new Thread(new Wget(url, speed, dirName, fileName));
            wget.start();
            wget.join();
        }
    }

    /**
     * Метод принимает строку URL и проверяет её на валидность.
     * @param url String с URL
     * @return boolean является ли строка ссылкой
     */
    public static boolean validateUrl(String url) {
        final String URL_REGEX =
                "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:]])?$";
        final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
        Matcher matcher = URL_PATTERN.matcher(url);

        return matcher.matches();
    }

    /**
     * Проверяет строку с названием файла на множество условий.
     * @param dirName String название папки, в которой будет лежать скаченный файл
     * @param fileName String название скачиваемого файла в файловой системе
     * @return boolean является ли будущее название валидным
     */
    public static boolean validateFileName(String dirName, String fileName) {
        if (fileName.length() < 5) {
            throw new IllegalArgumentException("Filename length must be more than 5");
        }

        File file = new File(String.format("%s/%s", dirName, fileName));

        if (file.exists()) {
            throw new IllegalArgumentException("You must set NOT EXIST FILE.");
        }

        if (file.isDirectory()) {
            throw new IllegalArgumentException("You must set FILE, not DIRECTORY.");
        }

        if (fileName.startsWith(".")) {
            throw new IllegalArgumentException("You must use \".filetype\" pattern.");
        }

        if (fileName.matches("(.[a-z]*)")) {
            throw new IllegalArgumentException("You must set filetypes");
        }

        return true;
    }
}
