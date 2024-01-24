package com.coffee.domain.cafe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;
    public List<MenuDto> getMenu() {
        return cafeRepository.findAll().stream()
                .map(entity -> MenuDto.builder()
                        .name(entity.getName())
                        .price(entity.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
