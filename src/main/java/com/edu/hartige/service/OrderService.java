package com.edu.hartige.service;

import com.edu.hartige.domain.BaseOrder;
import com.edu.hartige.domain.Order;
import com.edu.hartige.repository.BaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {

    private final BaseOrderRepository orderRepository;

    @Autowired
    public OrderService(BaseOrderRepository repo) {
        this.orderRepository = repo;
    }

    public ArrayList<BaseOrder> getProducts() {
        return (ArrayList<BaseOrder>) this.orderRepository.findAll();
    }

    public Order createProduct(Order order) {
        return this.orderRepository.save(order);
    }
}
