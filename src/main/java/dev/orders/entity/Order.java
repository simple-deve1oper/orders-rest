package dev.orders.entity;

import dev.orders.util.EnumTypePostgreSql;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "orders")
@TypeDef(
        name = "enum_postgresql",
        typeClass = EnumTypePostgreSql.class
)
/**
 * Класс для описания сущности Заказ
 * @version 1.0
 */
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    // идентификатор
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "status")
    @Type(type = "enum_postgresql")
    // текущий статус
    private Status currentStatus;
    @Column(name = "total_price", scale = 2)
    // итоговая цена
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    // корзина покупок
    private List<Basket> basket;

    public Order(Status currentStatus, BigDecimal totalPrice) {
        this.currentStatus = currentStatus;
        this.totalPrice = totalPrice;
    }
}
