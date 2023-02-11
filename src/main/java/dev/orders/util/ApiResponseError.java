package dev.orders.util;

import java.time.LocalDateTime;

/**
 * Класс для представления ответа с ошибкой
 * @param message - текст сообщение об ошибке
 * @param dateTime - дата и время
 */
public record ApiResponseError(String message, LocalDateTime dateTime) {
    public ApiResponseError(String message) {
        this(message, LocalDateTime.now());
    }
}
