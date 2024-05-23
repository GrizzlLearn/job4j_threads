package ru.job4j.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

/**
 * @author dl
 * @date 23.05.2024 21:34
 */
public class FileReader {
    private final File file;

    public FileReader(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return readContent(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return readContent(data -> data < 0x80);
    }

    /**
     * Читает содержимое файла и фильтрует его в соответствии с предоставленным предикатом фильтрации.
     * Только те символы, которые соответствуют предикату, будут включены в возвращаемое содержимое.
     *
     * @param filter {@code Predicate<Integer>}, определяющий условие для фильтрации символов.
     *               Если предикат возвращает {@code true} для целочисленного значения символа,
     *               этот символ включается в результат; в противном случае, он исключается.
     * @return {@code String}, содержащая отфильтрованное содержимое файла.
     * @throws IOException если возникает ошибка ввода-вывода при чтении файла.
     */
    private String readContent(Predicate<Integer> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream input = new FileInputStream(file)) {
            int data;
            while ((data = input.read()) != -1) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }
}
