package dev.orders.util;

import dev.orders.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для работы с файлами
 * @version 1.0
 */
public class FileUtils {
    /**
     * Создание директории с переданным именем, если не существует такой
     * @param directoryName - название директории
     */
    public static boolean createDirectory(String directoryName) throws IOException{
        boolean flag = false;
        Path path = Paths.get(directoryName);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
            flag = true;
        }

        return flag;
    }

    /**
     * Сохранение файла по переданному пути
     * @param file - файл, полученный из формы
     * @param pathToFile - путь к файлу
     */
    public static void saveFile(MultipartFile file, String pathToFile) throws IOException {
        Path path = Paths.get(pathToFile);
        file.transferTo(path);
    }

    /**
     * Валидация наименования файла с расширением
     * @param filename - наименование файла
     * @param errorMessages - список ошибок
     */
    public static void getErrorsForUploadedFile(String filename, List<String> errorMessages) {
        if (!Regex.checkText("[A-Za-z]+\\.[a-z]+", filename)) {
            errorMessages.add("Наименование файла может содержать только символы латиницы, например: 'file.jpg'");
        }
        if (!Regex.checkText(".{1,50}", filename)) {
            errorMessages.add("Наименование файла (с расширением) должно содержать не более 50 символов");
        }
        if (!Regex.checkText("jpeg|jpg|png", filename.split("\\.")[1])) {
            errorMessages.add("Расширения файлов доступны только следующие: jpeg, jpg и png");
        }
    }

    /**
     * Создание свободного имени из пришедшего в параметрах
     * @param filename - наименование файла
     * @return возвращает новое наименование файла
     */
    public static String makeFreeName(String filename) {
        String[] data = filename.split("\\.");
        if (data.length > 2) {
            throw new FileException("Неверно задано имя у передаваемого файла. Наименование файла (без расширения) не должно содержать символов точек");
        }
        long number = getLastNumberInName(data[0]);
        data[0] = data[0].replaceAll("\\d+", filename);
        String newFilename;
        if (number == 0) {
            newFilename = data[0] + 2;
        } else {
            newFilename = data[0] + (number + 1);
        }
        newFilename += String.format(".%s", data[1]);

        return newFilename;
    }

    /**
     * Получение числа из наименования файла
     * @param filename - наименование файла
     */
    private static long getLastNumberInName(String filename) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(filename);
        long number = 0;
        while (matcher.find()) {
            String temp = matcher.group();
            number = Long.valueOf(temp);
        }

        return number;
    }

}
