package com.coffee.api.payment.response;

import com.coffee.domain.payment.dto.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {
    private int remainPoint;
    public static PaymentResponse from(PaymentDto dto) {
        return new PaymentResponse(dto.getRemainPoint());
    }
}
