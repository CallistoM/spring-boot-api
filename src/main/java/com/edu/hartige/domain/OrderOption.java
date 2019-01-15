package com.edu.hartige.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class OrderOption extends DecoratedOrder {

    private String name;
    private int price;

    public OrderOption(String name, int price, BaseOrder order) {
        super(order);
        this.name = name;
        this.price = price;
    }

    public int price() {
        return this.price + baseOrder.price();
    }

    @Override
    public String toString() {
        return "option: " + name + ", " + baseOrder;
    }
}
