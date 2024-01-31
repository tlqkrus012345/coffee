package com.coffee.api.request;

import com.coffee.domain.order.dto.OrderDto;
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
