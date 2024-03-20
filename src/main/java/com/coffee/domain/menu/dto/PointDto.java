package com.coffee.domain.menu.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointDto {
    private Long memberId;
    private int point;
}
