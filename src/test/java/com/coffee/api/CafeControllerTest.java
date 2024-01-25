package com.coffee.api;

import com.coffee.api.request.ChargePointRequest;
import com.coffee.api.response.MenuResponse;
import com.coffee.domain.cafe.dto.MenuDto;
import com.coffee.domain.cafe.dto.PointDto;
import com.coffee.domain.cafe.service.CafeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CafeController.class)
class CafeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CafeService cafeService;

    @Test
    @DisplayName("커피 전체 메뉴 불러오기 테스트")
    void getMenuTest() throws Exception {
        when(cafeService.getMenu()).thenReturn(menuDtoList());
        List<MenuResponse> menuResponse = menuResponseList();

        mockMvc.perform(get("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(menuResponse)))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("포인트 충전 테스트")
    void chargePoint() throws Exception {
        ChargePointRequest chargePointRequest = ChargePointRequest.builder()
                .memberId(1L)
                .chargePoint(100)
                .build();

        PointDto pointDto = PointDto.builder()
                     .memberId(1L)
                     .point(100)
                     .build();

       doNothing().when(cafeService).chargePoint(pointDto);

       mockMvc.perform(post("/point")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(new ObjectMapper().writeValueAsString(chargePointRequest)))
                .andExpect(status().is2xxSuccessful());
    }

    private List<MenuDto> menuDtoList() {
        List<MenuDto> menuDtoList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            menuDtoList.add(MenuDto.builder()
                            .name(i + "커피")
                            .price(i * 100)
                    .build());
        }
        return menuDtoList;
    }
    private List<MenuResponse> menuResponseList() {
        List<MenuResponse> menuResponsesList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            menuResponsesList.add(MenuResponse.from(MenuDto.builder()
                    .name(i + "커피")
                    .price(i * 100)
                    .build()));
        }
        return menuResponsesList;
    }
}