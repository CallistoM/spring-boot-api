package com.edu.hartige.common.config;

import com.edu.hartige.domain.Order;
import com.edu.hartige.domain.Product;
import com.edu.hartige.domain.ProductCatalog;
import com.edu.hartige.repository.BaseOrderRepository;
import com.edu.hartige.repository.ProductCatalogRepository;
import com.edu.hartige.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ApplicationConfig {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

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

        // build product catalog, two products, and order
        ProductCatalog productCatalog = new ProductCatalog();

        // right productCatalog: without id; left productCatalog: with id
        // (needed because of autoincrement)
        productCatalog = productCatalogRepository.save(productCatalog);

        Product prod1 = new Product("Borrelnootjes", "Zakje borrelnootjes", 10);
        prod1 = productRepository.save(prod1);
        Product prod2 = new Product("Chips", "Zakje chips", 7);
        prod2 = productRepository.save(prod2);

        Order order = new Order();
        order = orderRepository.save(order);

        // build add two products
        productCatalog.add(prod1, 2);
        productCatalog.add(prod2, 3);
        productCatalogRepository.save(productCatalog);

        // "find" a product in the catalog and add it to the order
        Product prod = productCatalog.find(1L);

        // make a copy of the product (the copy has no id yet)
        // why a copy is made?
        Product prodCopy = new Product(prod);
        prodCopy = productRepository.save(prodCopy);

        logger.info("adding order");

        order.add(prodCopy);
        orderRepository.save(order);
    }


}
