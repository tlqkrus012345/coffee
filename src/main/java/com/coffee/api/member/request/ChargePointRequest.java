package com.coffee.api.member.request;

import com.coffee.domain.menu.dto.PointDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChargePointRequest {
    private Long memberId;
    private int chargePoint;
    public static PointDto from(ChargePointRequest chargePointRequest) {
        return PointDto.builder()
                .memberId(chargePointRequest.getMemberId())
                .point(chargePointRequest.getChargePoint())
                .build();
    }
}
