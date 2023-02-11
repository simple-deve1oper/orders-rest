package dev.orders.exception;

/**
 * Класс исключения для описания ошибок с существующими сущностями
 * @version 1.0
 */
public class EntityExistsException extends RuntimeException {
    public EntityExistsException() {}

    public EntityExistsException(String message) {
        super(message);
    }

    public EntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
