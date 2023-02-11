package dev.orders.controller;

import dev.orders.dto.BasketDTO;
import dev.orders.dto.OrderDTO;
import dev.orders.dto.OrderStatusDTO;
import dev.orders.entity.Basket;
import dev.orders.entity.Order;
import dev.orders.entity.Status;
import dev.orders.exception.EntityNotFoundException;
import dev.orders.service.BasketService;
import dev.orders.service.OrderService;
import dev.orders.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BasketService basketService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> ordersDTO = orderService.findAllOrders().stream()
                // .map(order -> DataConverting.convertOrderFromEntityToDto(order))
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();

        return ResponseEntity.ok(ordersDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.findOrderById(id);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDTO) {
        Order order = new Order();
        order.setCurrentStatus(Status.CREATE);
        order.setTotalPrice(new BigDecimal(0));
        order = orderService.saveOrder(order);

        List<BasketDTO> goodsDTO = orderDTO.getBasket();
        List<Basket> goods = goodsDTO.stream()
                .map(product -> modelMapper.map(product, Basket.class))
                .toList();

        for (Basket basket : goods) {
            basket.setOrder(order);
        }
        goods = basketService.saveAllBaskets(goods);

        order.setBasket(goods);
        BigDecimal totalPrice = new BigDecimal(0);

        for (Basket basket : goods) {
            BigDecimal price = basket.getProduct().getPrice();
            BigDecimal quantity = new BigDecimal(basket.getQuantity());
            BigDecimal tempPrice = new BigDecimal(0);
            tempPrice = tempPrice.add(price);
            tempPrice = tempPrice.multiply(quantity);
            totalPrice = totalPrice.add(tempPrice);
        }

        order.setTotalPrice(totalPrice);
        order = orderService.saveOrder(order);
        orderDTO = modelMapper.map(order, OrderDTO.class);

        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        String message = String.format("Заказ с идентификатором '%d' удалён", id);

        return ResponseEntity.ok(message);
    }

    @PutMapping
    public ResponseEntity<?> changeStatus(@RequestBody OrderStatusDTO orderStatusDTO) {
        Long id = orderStatusDTO.getId();
        String status = orderStatusDTO.getStatus();
        if (!StringUtils.checkStatus(status)) {
            throw new EntityNotFoundException(String.format("Статуса с наименованием '%s' не найдено", status));
        }
        Status currentStatus = Status.valueOf(status);

        boolean result = orderService.changeStatus(id, currentStatus);
        if (result) {
            Order order = orderService.findOrderById(id);
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

            return ResponseEntity.ok(orderDTO);
        } else {
            String message = "Вы не задали новый статус";

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }
}
