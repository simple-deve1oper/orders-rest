package dev.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * Класс для описания DTO типа Product
 * @version 1.0
 */
public class ProductDTO {
    // идентификатор
    private Long id;
    @NotEmpty(message = "Наименование товара не может быть пустым")
    @NotNull(message = "Наименование товара должно быть заполнено")
    @Size(min = 1, max = 100, message = "Наименование товара может содержать от 1 до 100 символов")
    // наименование
    private String name;
    @NotEmpty(message = "Описание товара не может быть пустым")
    @NotNull(message = "Описание товара должно быть заполнено")
    // описание
    private String description;
    @NotNull(message = "Вес товара должен быть заполнен")
    // вес/емкость
    private Double weight;
    @NotNull(message = "Единица измерения товара должна быть заполнена")
    // единица измерения
    private UnitDTO unit;
    @NotNull(message = "Цена товара должна быть заполнена")
    // цена
    private BigDecimal price;
    // изображение
    private String image;

    public ProductDTO(String name, String description, Double weight, UnitDTO unit, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.unit = unit;
        this.price = price;
    }
}
