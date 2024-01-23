package com.coffee.domain.cafe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;
    public List<Menu> getMenu() {
        return cafeRepository.findAll();
    }
}
