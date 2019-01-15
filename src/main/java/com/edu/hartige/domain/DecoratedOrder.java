package com.edu.hartige.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
abstract class DecoratedOrder extends BaseOrder {

    @OneToOne
    BaseOrder baseOrder;

    DecoratedOrder(BaseOrder baseOrder) {
        this.baseOrder = baseOrder;
    }

}
