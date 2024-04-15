package ru.job4j.thread;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author dl
 * @date 15.04.2024 20:20
 */
public class WgetValidator {
    private final String urlRegEx =
            "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:]])?$";
    private final String speedRegex = "-?\\d+(\\.\\d+)?";
    private final String[] args;

    public WgetValidator(String[] args) {
        this.args = args;
    }

    public boolean isValid() {
        return validateString(args[0], urlRegEx)
                && validateString(args[1], speedRegex)
                && validateFileName(args[2], args[3]);
    }

    /**
     * Проверяет соответствие строки паттерну
     *
     * @param string строка для проверки
     * @return {@code true}, если строка соответствует паттерну, {@code false} в противном случае.
     */
    private boolean validateString(String string, String regEx) {
        boolean result = false;
        final Pattern pattern = Pattern.compile(regEx);
        Optional<String> stringExist = Optional.ofNullable(string);
        if (stringExist.isPresent()) {
            result = pattern.matcher(stringExist.get()).matches();
        }

        return result;
    }

    /**
     * Проверяет строку с названием файла на множество условий.
     *
     * @param dirName  String название папки, в которой будет лежать скаченный файл
     * @param fileName String название скачиваемого файла в файловой системе
     * @return {@code true}, если будущее название является валидным, {@code false} в противном случае.
     */
    private boolean validateFileName(String dirName, String fileName) {

        Optional<String> dirNameExists = Optional.ofNullable(dirName);
        Optional<String> fileNameExists = Optional.ofNullable(fileName);

        if (dirNameExists.isEmpty() || fileNameExists.isEmpty()) {
            throw new IllegalArgumentException("validateFileName -> No arguments!");
        }

        if (dirNameExists.get().isEmpty()) {
            throw new IllegalArgumentException("Filename length must be more than 0");
        }

        if (fileNameExists.get().length() < 5) {
            throw new IllegalArgumentException("Filename length must be more than 5");
        }

        if (fileNameExists.get().startsWith(".")) {
            throw new IllegalArgumentException("You must use \".filetype\" pattern.");
        }

        if (fileNameExists.get().matches("(.[a-z]*)")) {
            throw new IllegalArgumentException("You must set filetypes");
        }

        String filePathString = String.format("%s/%s", dirNameExists.get(), fileNameExists.get());
        Path path = Paths.get(filePathString);

        if (Files.exists(path)) {
            throw new IllegalArgumentException("You must set NOT EXIST FILE.");
        }

        return true;
    }
}
