package com.coffee.domain.payment.service;

import com.coffee.domain.order.entity.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PaymentServiceTest {

    /*
    주문을 받고
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

        paymentService.pay(order);

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
}
