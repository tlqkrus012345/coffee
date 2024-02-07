package com.coffee.api.order.response;

import com.coffee.domain.order.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderResponse {
    private Long orderId;
    public static CreateOrderResponse from(OrderDto dto) {
        return new CreateOrderResponse(dto.getOrderId());
    }
}
