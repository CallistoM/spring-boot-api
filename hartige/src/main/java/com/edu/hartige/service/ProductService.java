package com.edu.hartige.service;

import com.edu.hartige.domain.Product;
import com.edu.hartige.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ArrayList<Product> getProducts() {
        return (ArrayList<Product>) this.productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }
}
