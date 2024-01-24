package com.coffee.api.response;

import com.coffee.domain.cafe.dto.OrderDetailDto;
import lombok.Builder;

@Builder
public class OrderDetailResponse {
    private String memberId;
    private String menuId;
    private int paymentPoint;
    private int remainPoint;

    public static OrderDetailResponse from(OrderDetailDto orderDetailDto) {
        return OrderDetailResponse.builder()
                .memberId(orderDetailDto.getMemberId())
                .menuId(orderDetailDto.getMenuId())
                .paymentPoint(orderDetailDto.getPaymentPoint())
                .remainPoint(orderDetailDto.getRemainPoint())
                .build();
    }
}
