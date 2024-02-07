package com.coffee.domain.cafe.service;

import com.coffee.domain.cafe.dto.MenuDto;
import com.coffee.domain.cafe.dto.PointDto;
import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.entity.Menu;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CafeServiceTest {
    @Mock
    private CafeRepository cafeRepository;
    @Mock
    private MemberRepository memberRepository;
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
     */
    @Test
    @DisplayName("인기 메뉴 목록 조회 테스트")
    void bestMenu() {

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