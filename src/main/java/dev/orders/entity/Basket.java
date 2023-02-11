package dev.orders.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "baskets")
/**
 * Класс для описания сущности товаров в корзине покупок (один объект - один товар в корзине покупок)
 * @version 1.0
 */
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    // идентификатор
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    // заказ
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    // товар
    private Product product;
    @Column(name = "quantity")
    // количество
    private Integer quantity;
}
