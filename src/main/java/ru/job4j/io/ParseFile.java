package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

/**
 * @author dl
 * @date 23.05.2024 20:50
 */

public class ParseFile {
    public final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return readContent(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
       return readContent(data -> data < 0x80);
    }

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

    public void saveContent(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }
}
