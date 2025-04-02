package com.rushi.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rushi.ordermanagement.dto.CustomerOrderReportDto;
import com.rushi.ordermanagement.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    
    @Query("SELECT new com.rushi.ordermanagement.dto.CustomerOrderReportDto(c.id, c.name, COUNT(o.id)) " +
            "FROM Order o JOIN o.customer c GROUP BY c.id, c.name")
     List<CustomerOrderReportDto> getTotalOrdersByCustomer();

     @Query("SELECT new com.rushi.ordermanagement.dto.CustomerOrderReportDto(c.id, c.name, COUNT(o.id)) " +
            "FROM Order o JOIN o.customer c GROUP BY c.id, c.name ORDER BY COUNT(o.id) DESC LIMIT 5")
     List<CustomerOrderReportDto> getTop5Customers();
}
