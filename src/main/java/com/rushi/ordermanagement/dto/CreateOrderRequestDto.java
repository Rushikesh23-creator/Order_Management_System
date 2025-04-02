package com.rushi.ordermanagement.dto;

import java.util.List;

public class CreateOrderRequestDto {
    private Long customerId;
    private List<OrderItemRequestDto> orderItems;
    
	public CreateOrderRequestDto(Long customerId, List<OrderItemRequestDto> orderItems) {
		super();
		this.customerId = customerId;
		this.orderItems = orderItems;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public List<OrderItemRequestDto> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemRequestDto> orderItems) {
		this.orderItems = orderItems;
	}
    
    
}
