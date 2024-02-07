package com.coffee.api.payment;

import com.coffee.api.payment.response.PaymentResponse;
import com.coffee.domain.payment.dto.PaymentDto;
import com.coffee.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/pay/{orderId}")
    public PaymentResponse pay(@PathVariable Long orderId) {
        return PaymentResponse.from(paymentService.pay(orderId));
    }
}
