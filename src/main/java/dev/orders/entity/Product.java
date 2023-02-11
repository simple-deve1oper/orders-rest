package dev.orders.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "goods")
/**
 * Класс для описания сущности Товар
 * @version 1.0
 */
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    // идентификатор
    private Long id;
    @Column(name = "name")
    // наименование
    private String name;
    @Column(name = "description")
    // описание
    private String description;
    @Column(name = "weight", scale = 2)
    // вес/емкость
    private Double weight;
    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    // единица измерения
    private Unit unit;
    @Column(name = "price", scale = 2)
    // цена
    private BigDecimal price;
    @Column(name = "image")
    // изображение
    private String image;

    @OneToMany(mappedBy = "product")
    // корзина покупок
    private List<Basket> basket;

    public Product(String name, String description, Double weight, Unit unit, BigDecimal price, String image) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.unit = unit;
        this.price = price;
        this.image = image;
    }
}
