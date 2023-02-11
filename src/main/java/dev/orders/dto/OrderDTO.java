package dev.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * Класс для описания DTO типа Order
 * @version 1.0
 */
public class OrderDTO {
    // идентификатор
    private Long id;
    // статус
    private String status;
    // итоговая цена
    private BigDecimal totalPrice;
    @NotBlank(message = "Корзина с товарами не может быть пустой")
    // корзина товаров
    private List<BasketDTO> basket;

    public OrderDTO(String status, BigDecimal totalPrice, List<BasketDTO> basket) {
        this.status = status;
        this.totalPrice = totalPrice;
        this.basket = basket;
    }
    public OrderDTO(String status, List<BasketDTO> basket) {
        this.status = status;
        this.basket = basket;
    }
    public OrderDTO(List<BasketDTO> basket) {
        this.basket = basket;
    }
}
