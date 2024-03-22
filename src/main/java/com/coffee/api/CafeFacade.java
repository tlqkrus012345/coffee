package com.coffee.api;

import com.coffee.domain.menu.service.MenuService;
import com.coffee.domain.menu.dto.PopularMenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CafeFacade {

    private final MenuService menuService;
    public List<PopularMenuDto> getPopularMenu(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        return menuService.getPopularMenu(startDate, endDate);
    }
}
