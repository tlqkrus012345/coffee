package com.coffee.api.cafe.request;

import com.coffee.domain.cafe.dto.PointDto;
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
