package com.coffee.api.order;

import com.coffee.api.order.request.CreateOrderRequest;
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
    @PostMapping
    public OrderDto createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(CreateOrderRequest.from(request));
    }
}
