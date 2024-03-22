package com.coffee.domain.payment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    private int remainPoint;
    private boolean isPaySuccess;
}
