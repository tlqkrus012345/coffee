package com.coffee.domain.order.entity;

import com.coffee.domain.payment.entity.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders", indexes = @Index(name = "idx_createdAt_menuName", columnList = "createdAt, menuName"))
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private Long memberId;
    private Long menuId;
    private String menuName;
    private int price;
    private Boolean isPaySuccess;
    private LocalDateTime createdAt;
    public void successOrder() {
        this.isPaySuccess = true;
    }
}
