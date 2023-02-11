package dev.orders.exception;

/**
 * Класс исключения для описания ошибок не найденных сущностей
 * @version 1.0
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {}

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
