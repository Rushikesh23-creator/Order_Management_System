package com.rushi.ordermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rushi.ordermanagement.dto.CreateOrderRequestDto;
import com.rushi.ordermanagement.dto.OrderDto;
import com.rushi.ordermanagement.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> placeOrder(@RequestBody CreateOrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.placeOrder(orderRequestDto));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDto>> getOrdersByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }
}
