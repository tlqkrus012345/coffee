package com.coffee.api;

import com.coffee.api.response.MenuResponse;
import com.coffee.domain.cafe.dto.MenuDto;
import com.coffee.domain.cafe.service.CafeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CafeController.class)
class CafeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CafeService cafeService;

    @Test
    void getMenuTest() throws Exception {
        when(cafeService.getMenu()).thenReturn(menuDtoList());
        List<MenuResponse> menuResponse = menuResponseList();

        mockMvc.perform(get("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(menuResponse)))
                .andExpect(status().isOk());
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