package com.coffee.domain.cafe.dto;

import com.coffee.domain.cafe.entity.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDto {
    private Long memberId;
    private Long menuId;
}
