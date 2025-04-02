package com.rushi.ordermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rushi.ordermanagement.dto.ProductDto;
import com.rushi.ordermanagement.entity.Product;
import com.rushi.ordermanagement.exception.ResourceNotFoundException;
import com.rushi.ordermanagement.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDto addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        Product savedProduct = productRepository.save(product);
        return new ProductDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(), savedProduct.getStock());
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(p -> new ProductDto(p.getId(), p.getName(), p.getPrice(), p.getStock()))
                .collect(Collectors.toList());
    }
    
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        Product updatedProduct = productRepository.save(product);
        return new ProductDto(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getPrice(), updatedProduct.getStock());
    }

}
