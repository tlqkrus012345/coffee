package com.coffee.domain.cafe.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointDto {
    private Long memberId;
    private int point;
}
