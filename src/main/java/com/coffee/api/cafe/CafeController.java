package com.coffee.api.cafe;

import com.coffee.api.cafe.request.BestMenuRequest;
import com.coffee.api.cafe.request.ChargePointRequest;
import com.coffee.api.cafe.response.BestMenuResponse;
import com.coffee.api.cafe.response.MenuResponse;
import com.coffee.domain.cafe.dto.BestMenuDto;
import com.coffee.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @PostMapping("/point")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void chargePoint(@RequestBody ChargePointRequest chargePointRequest) {
        cafeService.chargePoint(ChargePointRequest.from(chargePointRequest));
    }
    @PostMapping("/bestmenu")
    public BestMenuResponse bestMenu(@RequestBody BestMenuRequest bestMenuRequest) {
        BestMenuDto bestWeekMenu = cafeService.getBestWeekMenu(BestMenuRequest.from(bestMenuRequest));
        return BestMenuResponse.from(bestWeekMenu);
    }
}
