package com.coffee.intergration;

import com.coffee.api.response.OrderDetailRequest;
import com.coffee.api.response.OrderDetailResponse;
import com.coffee.domain.cafe.dto.OrderDetailDto;
import com.coffee.domain.cafe.dto.OrderDto;
import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.entity.Order;
import com.coffee.domain.cafe.service.CafeService;
import com.coffee.external.ExternalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CoffeeOrderTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CafeService cafeService;
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private ExternalService externalService;

    @Test
    @DisplayName("커피 주문/결제 테스트")
    void coffeeOrderTest() throws Exception {
        //given
        Long memberId = 1L;
        Long menuId = 1L;
        OrderDetailRequest orderDetailRequest = OrderDetailRequest.builder()
                .memberId(memberId)
                .menuId(menuId)
                .build();

        OrderDto orderDto = OrderDetailRequest.from(orderDetailRequest);
        //when
        Order order = cafeService.order(orderDto);

        OrderDetailDto result = cafeService.pay(order);

        externalService.send(result);
        //then
        OrderDetailResponse apiResult = OrderDetailResponse.from(result);

        mockMvc.perform(post("/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(apiResult)))
                .andExpect(status().isOk());
    }
}
