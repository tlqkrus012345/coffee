package com.coffee.api.order.request;

import com.coffee.domain.order.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderRequest {
    private Long memberId;
    private Long menuId;
    public static OrderDto from(CreateOrderRequest request) {
        return OrderDto.builder()
                .memberId(request.getMemberId())
                .menuId(request.getMenuId())
                .build();
    }
}
