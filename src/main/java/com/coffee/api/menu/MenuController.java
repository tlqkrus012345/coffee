package com.coffee.api.menu;

import com.coffee.api.menu.request.BestMenuRequest;
import com.coffee.api.menu.response.BestMenuResponse;
import com.coffee.api.menu.response.MenuResponse;
import com.coffee.domain.menu.dto.BestMenuDto;
import com.coffee.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/menu")
    public List<MenuResponse> getMenu() {
        return menuService.getMenu().stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());
    }
    @PostMapping("/bestmenu")
    public BestMenuResponse bestMenu(@RequestBody BestMenuRequest bestMenuRequest) {
        BestMenuDto bestWeekMenu = menuService.getBestWeekMenu(BestMenuRequest.from(bestMenuRequest));
        return BestMenuResponse.from(bestWeekMenu);
    }
}
