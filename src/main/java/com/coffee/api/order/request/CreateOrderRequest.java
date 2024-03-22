package com.coffee.api.order.request;

import com.coffee.domain.order.dto.CreateOrderDto;
import com.coffee.domain.order.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderRequest {

    private Long memberId;
    private Long menuId;

    public static CreateOrderDto from(CreateOrderRequest request) {
        return new CreateOrderDto(request.menuId, request.memberId);
    }
}
