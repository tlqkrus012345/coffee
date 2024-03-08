package com.coffee.domain.order.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT o.menuName FROM Order o WHERE o.createdAt >= :startDate AND o.createdAt <= :endDate")
    List<String> findMenuNamesBetweenDates(@Param("startDate") LocalDateTime startDateTime,@Param("endDate") LocalDateTime endDateTime);
}