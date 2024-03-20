package com.coffee.domain.order.repository;

import com.coffee.domain.order.dto.PopularMenuDto;

import java.time.LocalDate;
import java.util.List;

public interface OrderCustomRepository {
    List<PopularMenuDto> findPopularMenu(LocalDate start, LocalDate end);
}
