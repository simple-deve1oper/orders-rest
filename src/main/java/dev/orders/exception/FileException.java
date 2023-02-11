package dev.orders.exception;

/**
 * Класс исключений для описания ошибок файлов
 * @version 1.0
 */
public class FileException extends RuntimeException {
    public FileException() {}

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
