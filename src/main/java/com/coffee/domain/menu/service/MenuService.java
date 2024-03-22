package com.coffee.domain.menu.service;

import com.coffee.domain.menu.dto.MenuDto;
import com.coffee.domain.menu.entity.MenuRepository;
import com.coffee.domain.menu.dto.PopularMenuDto;
import com.coffee.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final static String POPULAR_MENU = "POPULAR_MENU";

    @Transactional(readOnly = true)
    public List<MenuDto> getMenu() {
        return menuRepository.findAll().stream()
                .map(entity -> MenuDto.builder()
                        .name(entity.getName())
                        .price(entity.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
    @Cacheable(POPULAR_MENU)
    @Transactional(readOnly = true)
    public List<PopularMenuDto> getPopularMenu(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findPopularMenu(startDate, endDate);
    }
    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(POPULAR_MENU)
    public void evictPopularMenuCache() {
    }
}
