package com.rushi.ordermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rushi.ordermanagement.dto.CustomerDto;
import com.rushi.ordermanagement.entity.Customer;
import com.rushi.ordermanagement.exception.ResourceNotFoundException;
import com.rushi.ordermanagement.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());

        Customer savedCustomer = customerRepository.save(customer);
        return new CustomerDto(savedCustomer.getId(), savedCustomer.getName(), savedCustomer.getEmail(), savedCustomer.getPhone());
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(c -> new CustomerDto(c.getId(), c.getName(), c.getEmail(), c.getPhone()))
                .collect(Collectors.toList());
    }
    
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());
        
        Customer updatedCustomer = customerRepository.save(customer);
        return new CustomerDto(updatedCustomer.getId(), updatedCustomer.getName(), updatedCustomer.getEmail(), updatedCustomer.getPhone());
    }

	public CustomerDto getCustomerById(Long id) {
		 Customer customer = customerRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
		return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
	}

 
}
