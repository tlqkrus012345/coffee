package com.coffee.domain.order.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Order> orders) {
        int batchSize = 100000;

        for (int i=0; i<orders.size(); i+=batchSize) {
            List<Order> batchOrders = orders.subList(i, Math.min(i + batchSize, orders.size()));
            insertBatch(batchOrders);
        }
    }
    private void insertBatch(List<Order> orders) {
        String sql = "INSERT INTO orders (member_id, menu_id, menu_name, price, is_pay_success, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Order order = orders.get(i);
                ps.setLong(1, order.getMemberId());
                ps.setLong(2, order.getMenuId());
                ps.setString(3, order.getMenuName());
                ps.setInt(4, order.getPrice());
                ps.setBoolean(5, order.getIsPaySuccess());
                ps.setTimestamp(6, Timestamp.valueOf(order.getCreatedAt()));
            }
            @Override
            public int getBatchSize() {
                return orders.size();
            }
        });
    }
}
