package dev.orders.repository;

import dev.orders.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    /**
     * Поиск объектов типа Basket по идентификатору объекта типа Order
     * @param id - идентификатор объекта типа Order
     * @return список объектов типа Basket
     */
    List<Basket> findByOrder_Id(Long id);
}
