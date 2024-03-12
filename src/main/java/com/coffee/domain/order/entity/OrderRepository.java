package com.coffee.domain.order.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDateTime, @Param("endDate") LocalDateTime endDateTime);
}