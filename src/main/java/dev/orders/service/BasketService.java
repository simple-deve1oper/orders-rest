package dev.orders.service;

import dev.orders.entity.Basket;
import dev.orders.repository.BasketRepository;
import dev.orders.repository.OrderRepository;
import dev.orders.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    /**
     * Поиск списка объектов типа Basket по идентификатору объекта типа Order
     * @param orderId - идентификатор объекта типа Order
     * @return список найденных объектов типа Basket
     */
    public List<Basket> findByOrder(Long orderId) {
        return basketRepository.findByOrder_Id(orderId);
    }

    /**
     * Сохранение списка объектов типа Basket
     * @param basket - список объектов типа Basket
     * @return список сохранённых объектов типа Basket
     */
    @Transactional
    public List<Basket> saveAllBaskets(List<Basket> basket) {
        return basketRepository.saveAll(basket);
    }
}
