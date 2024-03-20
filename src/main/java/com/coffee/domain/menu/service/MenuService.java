package com.coffee.domain.menu.service;

import com.coffee.domain.menu.dto.MenuDto;
import com.coffee.domain.menu.entity.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public List<MenuDto> getMenu() {
        return menuRepository.findAll().stream()
                .map(entity -> MenuDto.builder()
                        .name(entity.getName())
                        .price(entity.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
