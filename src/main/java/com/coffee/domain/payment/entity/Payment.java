package com.coffee.domain.payment.entity;

import com.coffee.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Order order;
}
