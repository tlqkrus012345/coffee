package com.coffee.domain.menu.service;

import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.member.service.MemberService;
import com.coffee.domain.menu.dto.MenuDto;
import com.coffee.domain.menu.dto.PointDto;
import com.coffee.domain.menu.entity.Menu;
import com.coffee.domain.menu.entity.MenuRepository;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MemberService memberService;
    @InjectMocks
    private MenuService menuService;
    private final Member member = Member.builder()
            .id(1L)
            .point(0)
            .build();

    @Test
    @DisplayName("커피 메뉴 불러오기 테스트")
    void getMenuTest() {
        //given
        when(menuRepository.findAll()).thenReturn(menuList());
        //when
        List<MenuDto> menu = menuService.getMenu();
        //then
        Assertions.assertThat(menu.get(0).getName()).isEqualTo("0커피");
        Assertions.assertThat(menu.get(0).getPrice()).isEqualTo(0);
    }
    @Test
    @DisplayName("포인트 충전 테스트")
    void chargePoint() {
        PointDto pointDto = PointDto.builder()
                .memberId(1L)
                .point(100)
                .build();
        when(memberRepository.findById(pointDto.getMemberId())).thenReturn(Optional.ofNullable((member)));

        memberService.chargePoint(pointDto);

        verify(memberRepository, times(1)).save(any(Member.class));
        Assertions.assertThat(member.getPoint()).isEqualTo(100);
    }
    private List<String> menuNameList() {
        List<String> list = new ArrayList<>();
        for (int i=0; i<=10; i++) {
            list.add("커피" + (i/3));
        }
        return list;
    }
    private List<Order> orderList() {
        List<Order> orderList = new ArrayList<>();
        for (int i=0; i<=10; i++) {
            orderList.add(Order.builder()
                    .memberId((long) i)
                    .menuId((long) i)
                    .price(1000)
                    .menuName("커피" + (i/3))
                    .createdAt(LocalDateTime.now().plusDays(i))
                    .isPaySuccess(false)
                    .build());
        }
        return orderList;
    }

    private List<Menu> menuList() {
        List<Menu> menuList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            menuList.add(Menu.builder()
                    .name(i + "커피")
                    .price(i * 100)
                    .build());
        }
        return menuList;
    }
}