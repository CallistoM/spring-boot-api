package com.edu.hartige.repository;


import com.edu.hartige.domain.ProductCatalog;
import org.springframework.data.repository.CrudRepository;

public interface ProductCatalogRepository extends CrudRepository<ProductCatalog, Long> {

}
