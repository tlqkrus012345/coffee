package com.coffee.domain.payment.service;

import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.entity.Menu;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.entity.OrderRepository;
import com.coffee.domain.payment.dto.PaymentDto;
import com.coffee.domain.payment.entity.Payment;
import com.coffee.domain.payment.entity.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final CafeRepository cafeRepository;
    public PaymentDto pay(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Member member = memberRepository.findById(order.getMemberId()).orElseThrow();
        int coffeePrice = order.getPrice();
        if (coffeePrice < member.getPoint()) {
            member.usePoint(coffeePrice);
            memberRepository.save(member);
        } else {
            throw new RuntimeException();
        }

        increaseMenuCnt(order.getMenuId());

        Payment payment = Payment.builder()
                .order(order)
                .build();
        paymentRepository.save(payment);
        return PaymentDto.builder()
                .remainPoint(member.getPoint())
                .build();
    }
    public void increaseMenuCnt(Long menuId) {
        Menu menu = cafeRepository.findById(menuId).orElseThrow();
        menu.increaseMenuCnt();
    }
}
