package com.coffee.api.response;

import com.coffee.domain.cafe.dto.OrderDto;
import lombok.Builder;

@Builder
public class OrderDetailRequest {
    private Long memberId;
    private Long menuId;
    public static OrderDto from(OrderDetailRequest orderDetailRequest) {
        return OrderDto.builder()
                .memberId(orderDetailRequest.memberId)
                .menuId(orderDetailRequest.menuId)
                .build();
    }
}
