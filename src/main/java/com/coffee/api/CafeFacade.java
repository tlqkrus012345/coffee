package com.coffee.api;

import com.coffee.domain.order.dto.PopularMenuDto;
import com.coffee.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CafeFacade {

    private final OrderService orderService;
    public List<PopularMenuDto> getPopularMenu(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        return orderService.getPopularMenu(startDate, endDate);
    }
}
