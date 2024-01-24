package com.coffee.api;

import com.coffee.api.response.MenuResponse;
import com.coffee.api.response.OrderDetailRequest;
import com.coffee.api.response.OrderDetailResponse;
import com.coffee.domain.cafe.dto.OrderDto;
import com.coffee.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CafeController {
    private final CafeService cafeService;
    @GetMapping("/menu")
    public List<MenuResponse> getMenu() {
        return cafeService.getMenu().stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());
    }
    @PostMapping("/order")
    public OrderDetailResponse order(@RequestBody OrderDetailRequest orderDetailRequest) {
        return OrderDetailResponse.from(cafeService.pay(cafeService.order(OrderDetailRequest.from(orderDetailRequest))));
    }
}
