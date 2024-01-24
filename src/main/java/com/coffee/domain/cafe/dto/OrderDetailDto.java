package com.coffee.domain.cafe.dto;

import lombok.Getter;

@Getter
public class OrderDetailDto {
    private String memberId;
    private String menuId;
    private int paymentPoint;
    private int remainPoint;
}
