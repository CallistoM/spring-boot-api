package com.edu.hartige.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order extends BaseOrder {

    private static final Logger logger = LoggerFactory.getLogger(Order.class);

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    private List<Product> orderItems = new ArrayList<>();

    public Order() {
    }

    public void add(Product p) {
        logger.debug("adding product with id " + p.getId());
        orderItems.add(p);
        logger.debug("Order is now: " + this.toString());
    }

    public Long getId() {
        return id;
    }

    public int price() {
        int price = 0;
        for (Product item : orderItems) {
            price += item.getPrice();
        }
        return price;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Product item : orderItems) {
            s.append("product: ").append(item.getName()).append("; ");
        }
        return s.toString();
    }
}
