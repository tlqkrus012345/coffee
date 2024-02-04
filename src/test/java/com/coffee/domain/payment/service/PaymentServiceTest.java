package com.coffee.domain.payment.service;

import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.entity.OrderRepository;
import com.coffee.domain.payment.entity.Payment;
import com.coffee.domain.payment.entity.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceTest {
    private PaymentService paymentService;
    private OrderRepository orderRepository;
    private MemberRepository memberRepository;
    private PaymentRepository paymentRepository;
    /*
    주문id를 받고 그 주문 id를 통해 회원과 메뉴를 가져온다
    회원의 포인트가 충분하다면 결제를 진행
    회원의 포인트를 감소
    payment 레포에 저장
     */
    @Test
    @DisplayName("커피 주문 완료 후 결제 테스트")
    void payTest() {
        Order order = Order.builder()
                .menuName("아이스 커피")
                .price(1000)
                .memberId(1L)
                .menuId(1L)
                .build();
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.ofNullable(order));

        paymentService.pay(orderId);

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
}
