package com.coffee.domain.cafe.service;

import com.coffee.domain.cafe.dto.BestMenuDto;
import com.coffee.domain.cafe.dto.MenuDto;
import com.coffee.domain.cafe.dto.PointDto;
import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.entity.Menu;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.entity.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CafeServiceTest {
    @Mock
    private CafeRepository cafeRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private CafeService cafeService;
    private final Member member = Member.builder()
            .id(1L)
            .point(0)
            .build();

    @Test
    @DisplayName("커피 메뉴 불러오기 테스트")
    void getMenuTest() {
        //given
        when(cafeRepository.findAll()).thenReturn(menuList());
        //when
        List<MenuDto> menu = cafeService.getMenu();
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

        cafeService.chargePoint(pointDto);

        verify(memberRepository, times(1)).save(any(Member.class));
        Assertions.assertThat(member.getPoint()).isEqualTo(100);
    }
    /*인기메뉴 목록 조회
    최근7일간인기있는메뉴3개를조회하는API.
    메뉴별주문횟수가정확해야합니다

    메뉴 엔티티에 cnt 컬럼을 추가 (메뉴 주문 시 동시성)
    메뉴레포에서 cnt 기준으로 상위 3개 메뉴를 불러온다 (메뉴 이름과 가격 그리고 주문 횟수)

    7일 데이터 가져온 뒤 order의 menuid를 카운트
     */
    @Test
    @DisplayName("인기 메뉴 목록 조회 테스트")
    void bestMenu() {
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(10);
        BestMenuDto bestMenuDto = new BestMenuDto();
        bestMenuDto.setStartDateTime(startDateTime);
        bestMenuDto.setEndDateTime(endDateTime);
        Map<String, Integer> result = Map.of("커피0", 3 , "커피1" , 3 , "커피2", 3);

        when(orderRepository.findByCreatedAtBetween(any(), any())).thenReturn(menuNameList());

        BestMenuDto bestWeekMenu = cafeService.getBestWeekMenu(bestMenuDto);
        Map<String, Integer> expected = bestWeekMenu.getBestMenuList();

        Assertions.assertThat((expected)).isEqualTo(result);
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