package com.edu.hartige.common.config;

import com.edu.hartige.domain.Order;
import com.edu.hartige.domain.Product;
import com.edu.hartige.domain.ProductCatalog;
import com.edu.hartige.repository.BaseOrderRepository;
import com.edu.hartige.repository.ProductCatalogRepository;
import com.edu.hartige.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ApplicationConfig {

    private final BaseOrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final ProductCatalogRepository productCatalogRepository;

    @Autowired
    public ApplicationConfig(
            BaseOrderRepository orderRepo,
            ProductRepository productRepo,
            ProductCatalogRepository productCatalogRepository) {
        this.orderRepository = orderRepo;
        this.productRepository = productRepo;
        this.productCatalogRepository = productCatalogRepository;
    }

    @PostConstruct
    public void createProductCatalogAndProductsAndOrder() {

        ProductCatalog productCatalog = new ProductCatalog();

        productCatalog = productCatalogRepository.save(productCatalog);

        Product prod1 = new Product("Borrelnootjes", "Zakje borrelnootjes", 10);
        prod1 = productRepository.save(prod1);
        Product prod2 = new Product("Chips", "Zakje chips", 7);
        prod2 = productRepository.save(prod2);

        Order order = new Order();
        orderRepository.save(order);

        productCatalog.add(prod1, 2);
        productCatalog.add(prod2, 3);
        productCatalogRepository.save(productCatalog);

//        Product prod = productCatalog.find(1L);

//        Product prodCopy = new Product(prod.getName(), prod.getDescription(), prod.getPrice());
//        prodCopy = productRepository.save(prodCopy);
//
//        logger.info("adding order");
//
//        order.add(prodCopy);
//        orderRepository.save(order);
    }


}
