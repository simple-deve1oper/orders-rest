package dev.orders.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "units")
/**
 * Класс для описания сущности Единица измерения
 * @version 1.0
 */
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    // идентификатор
    private Long id;
    @Column(name = "short_name")
    // сокращённое наименование
    private String shortName;
    @Column(name = "full_name")
    // полное наименование
    private String fullName;

    @OneToMany(mappedBy = "unit")
    // список товаров
    private List<Product> products;

    public Unit(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }
}
