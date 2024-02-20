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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    @DisplayName("낙관적 락 : 회원이 동시에 충전과 결제를 진행할 경우 OptimisticLockingFailureException 에러가 발생한다")
    void chargePointAndPay() throws InterruptedException {
        Member member = Member.builder().point(10000).build();
        Menu menu = Menu.builder().price(2000).name("아이스 커피").build();
        memberRepository.save(member);
        cafeRepository.save(menu);

        PointDto pointDto = PointDto.builder().point(5000).memberId(1L).build();
        OrderDto orderDto = OrderDto.builder().menuId(menu.getId()).memberId(member.getId()).build();

        OrderDto order = orderService.createOrder(orderDto);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<?> future = executorService.submit(
                () -> paymentService.pay(order.getOrderId()));

        Future<?> future2 = executorService.submit(
                () -> cafeService.chargePoint(pointDto));

        Exception result = new Exception();
        try {
            future.get();
            future2.get();
        } catch (Exception e) {
            result = (Exception) e.getCause();
        }

        Member chargeMember = memberRepository.findById(1L).get();
        int resultPoint = chargeMember.getPoint();
       // Assertions.assertThat(resultPoint).isEqualTo(13000);
        Assertions.assertThat(result).isInstanceOf(OptimisticLockingFailureException.class);
    }
}
