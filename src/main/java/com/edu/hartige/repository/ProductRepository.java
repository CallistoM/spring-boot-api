package com.edu.hartige.repository;


import com.edu.hartige.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
