package dev.orders.exception.handler;

import dev.orders.exception.EntityExistsException;
import dev.orders.exception.EntityNotFoundException;
import dev.orders.exception.EntityValidationException;
import dev.orders.exception.FileException;
import dev.orders.util.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
/**
 * Класс для описания обработчика исключений
 * @version 1.0
 */
public class ExceptionApiHandler {
    @ExceptionHandler
    public ResponseEntity<ApiResponseError> notFound(EntityNotFoundException exception) {
        ApiResponseError responseError = new ApiResponseError(exception.getMessage());

        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponseError> exists(EntityExistsException exception) {
        ApiResponseError responseError = new ApiResponseError(exception.getMessage());

        return new ResponseEntity<>(responseError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponseError> validation(EntityValidationException exception) {
        ApiResponseError responseError = new ApiResponseError(exception.getMessage());

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponseError> fileException(FileException exception) {
        ApiResponseError responseError = new ApiResponseError(exception.getMessage());

        return new ResponseEntity<>(responseError, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponseError> fileException(IOException exception) {
        ApiResponseError responseError = new ApiResponseError(exception.getMessage());

        return new ResponseEntity<>(responseError, HttpStatus.BAD_GATEWAY);
    }
}
