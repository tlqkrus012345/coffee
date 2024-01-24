package com.coffee.domain.cafe.service;

import com.coffee.domain.cafe.dto.OrderDetailDto;
import com.coffee.domain.cafe.dto.OrderDto;
import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.dto.MenuDto;
import com.coffee.domain.cafe.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;
    public List<MenuDto> getMenu() {
        return cafeRepository.findAll().stream()
                .map(entity -> MenuDto.builder()
                        .name(entity.getName())
                        .price(entity.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
    public Order order(OrderDto orderDto) {
        return new Order();
    }
    public OrderDetailDto pay(Order order) {
        //충전한 포인트에서 주문 금액을 차감한다.
        return new OrderDetailDto();
    }
}
