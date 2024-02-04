package com.coffee.api;

import com.coffee.api.cafe.request.ChargePointRequest;
import com.coffee.api.cafe.response.MenuResponse;
import com.coffee.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return OrderDetailResponse.from((cafeService.order(OrderDetailRequest.from(orderDetailRequest))));
    }
    @PostMapping("/point")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void chargePoint(@RequestBody ChargePointRequest chargePointRequest) {
        cafeService.chargePoint(ChargePointRequest.from(chargePointRequest));
    }
}
