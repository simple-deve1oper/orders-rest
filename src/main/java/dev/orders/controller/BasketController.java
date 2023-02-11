package dev.orders.controller;

import dev.orders.dto.BasketDTO;
import dev.orders.service.BasketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {
    @Autowired
    private BasketService basketService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{orderId}")
    public ResponseEntity<List<BasketDTO>> getBasketByOrderId(@PathVariable Long orderId) {
        List<BasketDTO> basketsDTO = basketService.findByOrder(orderId).stream()
                .map(basket -> modelMapper.map(basket, BasketDTO.class))
                .toList();

        return ResponseEntity.ok(basketsDTO);
    }
}
