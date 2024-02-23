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
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.concurrent.*;

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
    void optimisticLock() throws InterruptedException {
        Member member = Member.builder().point(10000).build();
        Menu menu = Menu.builder().price(2000).name("아이스 커피").build();
        memberRepository.save(member);
        cafeRepository.save(menu);

        PointDto pointDto = PointDto.builder().point(5000).memberId(1L).build();
        OrderDto orderDto = OrderDto.builder().menuId(1L).memberId(1L).build();

        OrderDto order = orderService.createOrder(orderDto);
        // 스레드 풀을 만들고 작업을 비동기적으로 실행이 가능하고 작업의 완료를 기다린다.
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

        Assertions.assertThat(result).isInstanceOf(OptimisticLockingFailureException.class);
    }
    @Test
    @DisplayName("비관적 락 : 회원이 동시에 충전을 두 번할 경우 200 충전")
    void pessimisticLock() throws InterruptedException {
        Member member = Member.builder().point(0).build();
        Long memberId = memberRepository.save(member).getId();

        PointDto pointDto = PointDto.builder().memberId(memberId).point(100).build();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(2);

        System.out.println("테스트 시작");
        for (int i = 0; i < 2; i++) {
            executorService.submit(() -> {
                try {
                    cafeService.chargePoint(pointDto);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        System.out.println("결과 검증");
        List<Member> all = memberRepository.findAll();
        int point1 = all.get(0).getPoint();
        Assertions.assertThat(point1).isEqualTo(200);
    }
    @Test
    @DisplayName("분산 락 : ")
    void distributedLock() {

    }
    public static class RedissonConfig {
        private static final String REDISSON_HOST_PREFIX = "redis://";
        public RedissonClient redissonClient() {
            RedissonClient redissonClient = null;
            Config config = new Config();
            config.useSingleServer().setAddress(REDISSON_HOST_PREFIX + redisContainer.getHost() + ":" + redisContainer.getFirstMappedPort());
            redissonClient = Redisson.create(config);
            return redissonClient;
        }
    }
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DistributedLock {
    }
}
