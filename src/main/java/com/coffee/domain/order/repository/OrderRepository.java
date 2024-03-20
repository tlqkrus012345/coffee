package com.coffee.domain.order.repository;

import com.coffee.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {
    @Query(value = "SELECT o.menuName FROM Order o WHERE o.createdAt >= :startDateTime AND o.createdAt <= :endDateTime")
    List<String> findByCreatedAtBetween(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);
}
