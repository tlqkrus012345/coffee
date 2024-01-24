package com.coffee.domain.cafe.dto;

import lombok.Builder;

@Builder
public class OrderDto {
    private Long memberId;
    private Long menuId;
}
