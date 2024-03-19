package com.coffee.domain.order.service;

import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.entity.Menu;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.order.dto.OrderDto;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;
    private final OrderRepository orderRepository;
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Long memberId = orderDto.getMemberId();
        Long menuId = orderDto.getMenuId();

        memberRepository.existsById(memberId);
        Menu menu = cafeRepository.findById(menuId)
                .orElseThrow(IllegalArgumentException::new);

        Order order = Order.builder()
                .memberId(memberId)
                .menuId(menuId)
                .price(menu.getPrice())
                .menuName(menu.getName())
                .createdAt(LocalDateTime.now())
                .isPaySuccess(false)
                .build();

        Order savedOrder = orderRepository.save(order);
        return OrderDto.builder()
                .orderId(savedOrder.getId())
                .build();
    }
}
