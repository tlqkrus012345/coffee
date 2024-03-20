package com.coffee.domain.menu.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class BestMenuDto {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Map<String, Integer> bestMenuList = new HashMap<>();
}
