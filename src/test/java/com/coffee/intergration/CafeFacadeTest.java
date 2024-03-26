package com.coffee.intergration;

import com.coffee.api.CafeFacade;
import com.coffee.api.event.CreatedOrderEvent;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.menu.entity.Menu;
import com.coffee.domain.menu.entity.MenuRepository;
import com.coffee.domain.order.dto.CreateOrderDto;
import com.coffee.domain.payment.dto.PaymentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;

@RecordApplicationEvents
public class CafeFacadeTest extends AbstractIntegrationTest {
    @Autowired
    private CafeFacade cafeFacade;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ApplicationEvents applicationEvents;
    private Menu menu;
    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder().point(10000).build();
        memberRepository.save(member);

        menu = Menu.builder().cnt(1).price(1000).name("커피").build();
        menuRepository.save(menu);
    }
    /**
     * 주문 내역을 외부 플랫폼에 전송하는 기능
     * 주문 결제 로직은 외부 플랫폼 전송 이벤트 로직과 별도의 트랜잭션으로 관리한다
     * 전송 로직은 비동기로 처리하고 주문 결제 로직이 성공해야 전송이 완료된다
     */
    @Test
    @DisplayName("주문 결제 로직과 전송 로직 테스트")
    @Transactional
    public void Async_OrderAndPay_Send() {
        CreateOrderDto createOrderDto = CreateOrderDto.builder().memberId(1L).menuId(1L).build();
        PaymentDto paymentDto = cafeFacade.orderAndPay(createOrderDto);

        Assertions.assertEquals(paymentDto.getRemainPoint(), 10000 - 1000);

        int count = (int) applicationEvents.stream(CreatedOrderEvent.class).count();
        Assertions.assertEquals(1, count);
    }
}
