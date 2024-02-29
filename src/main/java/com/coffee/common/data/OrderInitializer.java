package com.coffee.common.data;

import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.entity.OrderBulkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class OrderInitializer implements ApplicationRunner {
    private final OrderBulkRepository orderBulkRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Order> orders = new ArrayList<>();
        Random random = new Random();
        LocalDateTime startDate = LocalDateTime.of(2024,1,28,0,0,0);
        LocalDateTime endDate = LocalDateTime.of(2024,2,28,23,59,59);

        for (int i=0; i<10000; i++) {
            long randomId = random.nextLong(10) + 1;
            Order order = Order.builder()
                    .memberId(i+1L)
                    .menuId(randomId)
                    .price(i/100)
                    .menuName(randomId + " 커피")
                    .isPaySuccess(true)
                    .createdAt(generateRandomDateTime(startDate, endDate))
                    .build();
            orders.add(order);
        }
        orderBulkRepository.saveAll(orders);
    }
    public static LocalDateTime generateRandomDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        long startEpochMilli = startDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endEpochMilli = endDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        long randomEpochMilli = ThreadLocalRandom.current().nextLong(startEpochMilli, endEpochMilli);

        return LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(randomEpochMilli), ZoneId.systemDefault());
    }
}
