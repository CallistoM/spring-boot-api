package com.edu.hartige.repository;


import com.edu.hartige.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
