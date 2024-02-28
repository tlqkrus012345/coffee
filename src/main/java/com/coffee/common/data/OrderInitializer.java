package com.coffee.common.data;

import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.entity.OrderBulkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
            Order order = Order.builder()
                    .memberId(i+1L)
                    .menuId(random.nextLong(10) + 1)
                    .price(i/100)
                    .menuName(i + " 커피")
                    .isPaySuccess(true)
                    .createdAt(generateRandomDateTime(startDate, endDate))
                    .build();
            orders.add(order);
        }
        orderBulkRepository.saveAll(orders);
    }
    public static LocalDateTime generateRandomDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        long startEpochSecond = startDate.toEpochSecond(ZoneOffset.UTC);
        long endEpochSecond = endDate.toEpochSecond(ZoneOffset.UTC);

        long randomEpochSecond = ThreadLocalRandom.current().nextLong(startEpochSecond, endEpochSecond);

        return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC);
    }
}
