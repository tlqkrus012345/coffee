package com.coffee.domain.cafe.service;

import com.coffee.domain.cafe.dto.MenuDto;
import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.entity.Menu;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CafeServiceTest {
    @Mock
    private CafeRepository cafeRepository;
    @InjectMocks
    private CafeService cafeService;

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