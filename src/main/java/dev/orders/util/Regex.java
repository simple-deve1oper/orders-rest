package dev.orders.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для работы с регулярными выражениями
 * @version 1.0
 */
public class Regex {
    /**
     * Метод для проверки текста регулярным выражением
     * @param regexp - регулярное выражение
     * @param text - текст
     */
    public static boolean checkText(String regexp, String text) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

}
