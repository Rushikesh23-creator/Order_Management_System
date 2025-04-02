package com.rushi.ordermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rushi.ordermanagement.dto.CustomerOrderReportDto;
import com.rushi.ordermanagement.service.OrderService;

@RestController
@RequestMapping("/api/reporting")
public class ReportingController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/customer-orders")
    public ResponseEntity<List<CustomerOrderReportDto>> getTotalOrdersByCustomer() {
        return ResponseEntity.ok(orderService.getTotalOrdersByCustomer());
    }

    @GetMapping("/top-customers")
    public ResponseEntity<List<CustomerOrderReportDto>> getTop5Customers() {
        return ResponseEntity.ok(orderService.getTop5Customers());
    }
}
