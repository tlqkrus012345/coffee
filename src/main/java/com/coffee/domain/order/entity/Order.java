package com.coffee.domain.order.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private Long memberId;
    private Long menuId;
    private String menuName;
    private int price;
    private Boolean isSuccess;
}
