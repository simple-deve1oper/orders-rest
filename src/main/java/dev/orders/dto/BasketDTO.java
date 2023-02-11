package dev.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * Класс для описания DTO типа Basket
 * @version 1.0
 */
public class BasketDTO {
    @NotNull(message = "Товар должен быть заполнен")
    // товар
    private ProductDTO product;
    @NotNull(message = "Количество товара должно быть заполнено")
    // количество
    private Integer quantity;
}
