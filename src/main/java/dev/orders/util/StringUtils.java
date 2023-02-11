package dev.orders.util;

import dev.orders.entity.Status;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;

/**
 * Класс для работы со строками
 */
public class StringUtils {
    /**
     * Получение строки ошибок из объекта типа BindingResult
     * @return строка с ошибками
     */
    public static String getErrorsFromValidation(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder errors = new StringBuilder();
        fieldErrors.stream().forEach(
                fieldError -> errors.append(String.format("%s;", fieldError.getDefaultMessage()))
        );

        return errors.toString();
    }

    /**
     * Проверка значения в перечислении типа Status
     * @param currentStatus - статус заказа
     * @return результат проверки статуса в перечислении Status
     */
    public static boolean checkStatus(String currentStatus) {
        List<String> statuses = Arrays.asList(Status.values()).stream()
                .map(status -> status.toString())
                .toList();
        return statuses.contains(currentStatus);
    }
}
