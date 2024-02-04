package com.coffee.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDto {
    private Long memberId;
    private Long menuId;
    private Long orderId;
}
