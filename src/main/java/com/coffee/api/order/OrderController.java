package com.coffee.api.order;

import com.coffee.api.order.request.CreateOrderRequest;
import com.coffee.api.order.response.CreateOrderResponse;
import com.coffee.domain.order.dto.OrderDto;
import com.coffee.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/order")
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        OrderDto dto = orderService.createOrder(CreateOrderRequest.from(request));
        return CreateOrderResponse.from(dto);
    }
}
