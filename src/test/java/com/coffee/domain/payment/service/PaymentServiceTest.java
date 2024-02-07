package com.coffee.domain.payment.service;

import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.entity.Menu;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.entity.OrderRepository;
import com.coffee.domain.payment.entity.Payment;
import com.coffee.domain.payment.entity.PaymentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CafeRepository cafeRepository;
    /*
    커피 Order가 성공한 뒤 결제가 성공한다
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
        Member member = Member.builder()
                .point(10000)
                .build();
        Menu menu = Menu.builder()
                .name("아이스 커피")
                .price(1500)
                .cnt(0)
                .build();

        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.ofNullable(order));
        when(memberRepository.findById(order.getMemberId())).thenReturn(Optional.ofNullable(member));
        when(cafeRepository.findById(order.getMenuId())).thenReturn(Optional.ofNullable(menu));

        paymentService.pay(orderId);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        Assertions.assertThat(member.getPoint()).isEqualTo(9000);
    }
    /*
    유저의 포인트가 결제되면 Menu의 cnt가 증가한다
     */
    @Test
    @DisplayName("커피 주문 결제 완료 후 메뉴의 cnt 컬럼 증가 테스트")
    void increaseMenuCntTest() {
        Long menuId = 1L;
        Menu menu = Menu.builder()
                .name("아이스 커피")
                .price(1500)
                .cnt(0)
                .build();
        when(cafeRepository.findById(menuId)).thenReturn(Optional.ofNullable(menu));

        paymentService.increaseMenuCnt(menuId);

        Assertions.assertThat(menu.getCnt()).isEqualTo(1);
    }
    @Test
    @DisplayName("커피 주문 결제 완료 후 오더의 isPaySuccess가 True 테스트")
    void isPaySuccessTest() {
        Order order = Order.builder()
                .memberId(1L)
                .menuId(1L)
                .price(1000)
                .menuName("아이스 커피")
                .createdAt(LocalDateTime.now())
                .isPaySuccess(false)
                .build();

        paymentService.isPaySuccess(order);

        Assertions.assertThat(order.getIsPaySuccess()).isTrue();
    }
}
