package com.coffee.api.order;

import com.coffee.api.CafeFacade;
import com.coffee.api.order.request.CreateOrderRequest;
import com.coffee.api.order.response.PaymentResponse;
import com.coffee.domain.order.dto.CreateOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final CafeFacade cafeFacade;
    @PostMapping("/order")
    public PaymentResponse createOrder(@RequestBody CreateOrderRequest request) {
        CreateOrderDto dto = CreateOrderRequest.from(request);
        return PaymentResponse.from(cafeFacade.orderAndPay(dto));
    }
}
