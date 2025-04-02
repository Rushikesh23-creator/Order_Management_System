package com.rushi.ordermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rushi.ordermanagement.dto.CreateOrderRequestDto;
import com.rushi.ordermanagement.dto.CustomerOrderReportDto;
import com.rushi.ordermanagement.dto.OrderDto;
import com.rushi.ordermanagement.dto.OrderItemDto;
import com.rushi.ordermanagement.entity.Customer;
import com.rushi.ordermanagement.entity.Order;
import com.rushi.ordermanagement.entity.OrderItem;
import com.rushi.ordermanagement.entity.Product;
import com.rushi.ordermanagement.exception.InsufficientStockException;
import com.rushi.ordermanagement.exception.OrderProcessingException;
import com.rushi.ordermanagement.exception.ResourceNotFoundException;
import com.rushi.ordermanagement.repository.CustomerRepository;
import com.rushi.ordermanagement.repository.OrderItemRepository;
import com.rushi.ordermanagement.repository.OrderRepository;
import com.rushi.ordermanagement.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public OrderDto placeOrder(CreateOrderRequestDto requestDto) {
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);

        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);

        List<OrderItem> orderItems = requestDto.getOrderItems().stream().map(orderItemRequestDto -> {
            Product product = productRepository.findById(orderItemRequestDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < orderItemRequestDto.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - orderItemRequestDto.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequestDto.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequestDto.getQuantity())));

            totalAmount.updateAndGet(amount -> amount.add(orderItem.getPrice()));

            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount.get());

        Order savedOrder;
        
        try {
        	savedOrder = orderRepository.save(order);
        	orderItemRepository.saveAll(orderItems);
        } catch (Exception e) {
            throw new OrderProcessingException("Failed to process the order");
        }
        
        return new OrderDto(
                savedOrder.getId(),
                customer.getId(),
                customer.getName(),
                orderItems.stream().map(oi -> new OrderItemDto(
                        oi.getProduct().getId(),
                        oi.getProduct().getName(),
                        oi.getQuantity(),
                        oi.getPrice()
                )).collect(Collectors.toList()),
                totalAmount.get()
        );
        
        
    }

    public List<OrderDto> getOrdersByCustomerId(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        try {
        	return orders.stream().map(order -> new OrderDto(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getOrderItems().stream().map(oi -> new OrderItemDto(
                        oi.getProduct().getId(),
                        oi.getProduct().getName(),
                        oi.getQuantity(),
                        oi.getPrice()
                )).collect(Collectors.toList()),
                order.getTotalAmount()
        )).collect(Collectors.toList());
       } catch (Exception e) {
            throw new OrderProcessingException("Failed to retrieve order items");
        }
    }
    
    public List<CustomerOrderReportDto> getTotalOrdersByCustomer() {
        return orderRepository.getTotalOrdersByCustomer();
    }

    public List<CustomerOrderReportDto> getTop5Customers() {
        return orderRepository.getTop5Customers();
    }
}
