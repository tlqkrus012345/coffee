package com.coffee.api.menu;

import com.coffee.api.CafeFacade;
import com.coffee.api.menu.request.PopularMenuRequest;
import com.coffee.api.menu.response.MenuResponse;
import com.coffee.domain.menu.service.MenuService;
import com.coffee.domain.order.dto.PopularMenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final CafeFacade cafeFacade;

    @GetMapping("/menu")
    public List<MenuResponse> getMenu() {
        return menuService.getMenu().stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());
    }
    @PostMapping("/popular-menu")
    public List<PopularMenuDto> getPopularMenu(@RequestBody PopularMenuRequest request) {
        return cafeFacade.getPopularMenu(request.getStartDateTime(), request.getEndDateTime());
    }
}
