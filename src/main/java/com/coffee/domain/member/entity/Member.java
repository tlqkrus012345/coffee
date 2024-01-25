package com.coffee.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
    private Long id;
    private int point;
    public void chargePoint(int point) {
        this.point += point;
    }
}
