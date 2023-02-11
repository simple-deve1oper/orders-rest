package dev.orders.exception;

/**
 * Класс исключений для описания сущностей, которые не прошли валидацию
 * @version 1.0
 */
public class EntityValidationException extends RuntimeException {
    public EntityValidationException() {}

    public EntityValidationException(String message) {
        super(message);
    }

    public EntityValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
