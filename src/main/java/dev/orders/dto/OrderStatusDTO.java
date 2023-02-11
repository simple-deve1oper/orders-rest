package dev.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * Класс для описания DTO типа Order для смены статуса
 * @version 1.0
 */
public class OrderStatusDTO {
    @NotNull(message = "Идентификатор должен быть заполнен")
    // идентификатор
    private Long id;
    @NotBlank(message = "Статус должен быть заполнен")
    // статус
    private String status;
}
