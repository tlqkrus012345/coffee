package com.coffee.intergration;

import com.coffee.domain.order.dto.OrderDto;
import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.entity.OrderRepository;
import com.coffee.domain.cafe.service.CafeService;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.external.ExternalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    private MemberRepository memberRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ExternalService externalService;
    private final Member member = Member.builder()
            .id(1L)
            .point(0)
            .build();

    @Test
    @DisplayName("커피 주문/결제 테스트")
    void coffeeOrderTest() throws Exception {
        //given
//        Long memberId = 1L;
//        Long menuId = 1L;
//        OrderDetailRequest orderDetailRequest = OrderDetailRequest.builder()
//                .memberId(memberId)
//                .menuId(menuId)
//                .build();
//
//        OrderDto orderDto = OrderDetailRequest.from(orderDetailRequest);
//        when(cafeService.order(orderDto)).thenReturn(any());

        //when
//        cafeService.order(orderDto);
//
//        externalService.send();

        //then
        verify(orderRepository, times(1)).save(any(Order.class));
        //Assertions.assertThat()
        //OrderDetailResponse apiResult = OrderDetailResponse.from(result);

//        mockMvc.perform(post("/order")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(new ObjectMapper().writeValueAsString(orderDetailRequest)))
//                .andExpect(status().isOk());
    }
}
