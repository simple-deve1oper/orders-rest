package dev.orders.service;

import dev.orders.entity.Order;
import dev.orders.entity.Status;
import dev.orders.exception.EntityNotFoundException;
import dev.orders.repository.OrderRepository;
import dev.orders.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Поиск списка всех объектов типа Order
     * @return список объектов типа Order
     */
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Поиск объекта типа Order по идентификатору
     * @param id - идентиифкатор
     * @return объект типа Order
     */
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Заказ с идентификатором %d не найден", id)));
    }

    /**
     * Сохранение объекта типа Order
     * @param order - объект типа Order
     * @return сохранённый объект типа Order
     */
    @Transactional
    public Order saveOrder(Order order) {
        String status = order.getCurrentStatus().toString();
        if (!StringUtils.checkStatus(status)) {
            throw new EntityNotFoundException(String.format("Статуса с наименованием '%s' не найдено", status));
        }

        return orderRepository.save(order);
    }

    /**
     * Изменение статуса у объекта типа Order
     * @param id - идентификатор
     * @param status - статус
     * @return возращает результат изменения статуса заказа
     */
    @Transactional
    public boolean changeStatus(Long id, Status status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            new EntityNotFoundException(String.format("Заказ с идентификатором %d не найден", id));
        }

        if (!StringUtils.checkStatus(status.toString())) {
            throw new EntityNotFoundException(String.format("Статуса с наименованием '%s' не найдено", status));
        }

        Order order = optionalOrder.get();
        if (order.getCurrentStatus() == status) {
            return false;
        }
        order.setCurrentStatus(status);
        orderRepository.save(order);

        return true;
    }

    /**
     * Удаление объекта типа Order по идентификатору
     * @param id - идентификатор
     */
    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Заказ с идентификатором %d не найден для удаления", id));
        }

        orderRepository.deleteById(id);
    }
}
