package com.coffee.api.order.response;

import com.coffee.domain.payment.dto.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {

    private int remainPoint;
    private boolean isPaySuccess;

    public static PaymentResponse from(PaymentDto dto) {
        return new PaymentResponse(dto.getRemainPoint(), dto.isPaySuccess());
    }
}
