package com.coffee.api;

import com.coffee.domain.cafe.CafeService;
import com.coffee.domain.cafe.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CafeController {
    private final CafeService cafeService;
    @GetMapping("/menu")
    public List<Menu> getMenu() {
        return cafeService.getMenu();
    }
}
