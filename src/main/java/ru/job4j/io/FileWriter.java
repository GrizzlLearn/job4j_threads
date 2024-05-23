package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author dl
 * @date 23.05.2024 21:34
 */
public class FileWriter {
    private final File file;

    public FileWriter(File file) {
        this.file = file;
    }

    /**
     * Сохраняет предоставленное содержимое в файл с использованием кодировки UTF-8.
     *
     * @param content строка, содержащая текст для сохранения в файл.
     * @throws IOException если возникает ошибка ввода-вывода при записи в файл.
     */
    public void saveContent(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }
}
