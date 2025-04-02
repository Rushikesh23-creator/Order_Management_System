package com.rushi.ordermanagement.dto;

public class CustomerOrderReportDto {
    private Long customerId;
    private String customerName;
    private Long totalOrders;

    public CustomerOrderReportDto(Long customerId, String customerName, Long totalOrders) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalOrders = totalOrders;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }
}
