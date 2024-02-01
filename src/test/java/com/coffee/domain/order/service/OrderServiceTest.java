package com.coffee.domain.order.service;

import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.entity.Menu;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.order.dto.OrderDto;
import com.coffee.domain.order.entity.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private CafeRepository cafeRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OrderRepository orderRepository;

    /*
    사용자 식별값, 메뉴 ID를 받는다
    메뉴 ID와 사용자 식별 값을 통해 데이터를 가져온다
    오더를 생성한다 -> 오더를 저장한다
     */
    @Test
    @DisplayName("커피 주문 테스트")
    void orderTest() {
        Long memberId = 1L;
        Long menuId = 1L;
        OrderDto orderDto = OrderDto.builder()
                .memberId(memberId)
                .menuId(menuId)
                .build();
        Menu menu = Menu.builder()
                .name("아이스 커피")
                .price(1000)
                .build();

        when(memberRepository.existsById(memberId)).thenReturn(true);
        when(cafeRepository.findById(menuId)).thenReturn(Optional.ofNullable(menu));

        orderService.createOrder(orderDto);

        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
