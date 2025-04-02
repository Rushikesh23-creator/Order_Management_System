package com.rushi.ordermanagement.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {
    private Long id;
    private Long customerId;
    private String customerName;
    private List<OrderItemDto> orderItems;
    private BigDecimal totalAmount;
    
	public OrderDto(Long id, Long customerId, String customerName, List<OrderItemDto> orderItems,
			BigDecimal totalAmount) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.customerName = customerName;
		this.orderItems = orderItems;
		this.totalAmount = totalAmount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
    
    
}
