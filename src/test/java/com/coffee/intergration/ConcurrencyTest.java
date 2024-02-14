package com.coffee.intergration;

import com.coffee.domain.cafe.dto.PointDto;
import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.entity.Menu;
import com.coffee.domain.cafe.service.CafeService;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.order.dto.OrderDto;
import com.coffee.domain.order.service.OrderService;
import com.coffee.domain.payment.dto.PaymentDto;
import com.coffee.domain.payment.service.PaymentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConcurrencyTest extends AbstractIntegrationTest{
    @Autowired
    CafeService cafeService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    OrderService orderService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CafeRepository cafeRepository;
    /*
    회원이 충전과 동시에 결제를 진행한 경우
    회원 기존 포인트 : 10000
    충전 : 5000
    결제 : 2000
    실제 결과 : 13000
     */
    @Test
    void chargePointAndPay() throws InterruptedException {
        Member member = Member.builder().point(10000).build();
        Menu menu = Menu.builder().price(2000).name("아이스 커피").build();
        memberRepository.save(member);
        cafeRepository.save(menu);

        PointDto pointDto = PointDto.builder().point(5000).memberId(1L).build();
        OrderDto orderDto = OrderDto.builder().menuId(menu.getId()).memberId(member.getId()).build();

        OrderDto order = orderService.createOrder(orderDto);

        Runnable chargeTask = () -> {
            cafeService.chargePoint(pointDto);
        };
        Runnable payTask = () -> {
            paymentService.pay(order.getOrderId());

        };

        Thread thread1 = new Thread(chargeTask);
        Thread thread2 = new Thread(payTask);

        thread1.start();
        thread2.start();
//        thread2.join();
        thread1.join();


        Member chargeMember = memberRepository.findById(1L).get();
        int resultPoint = chargeMember.getPoint();
        Assertions.assertThat(resultPoint).isEqualTo(13000);
    }
}
