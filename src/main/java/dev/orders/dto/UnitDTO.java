package dev.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * Класс для описания DTO типа Unit
 * @version 1.0
 */
public class UnitDTO {
    @NotEmpty(message = "Сокращённное наименование не может быть пустым")
    @NotNull(message = "Сокращенное наименование должно быть заполнено")
    @Size(min = 1, max = 10, message = "Сокращенное наименование может содержать от 1 до 10 символов")
    // сокращённое наименование
    private String shortName;
    @NotEmpty(message = "Полное наименование не может быть пустым")
    @NotNull(message = "Полное наименование должно быть заполнено")
    @Size(min = 1, max = 100, message = "Полное наименование может содержать от 1 до 100 символов")
    // полное наименование
    private String fullName;
}
