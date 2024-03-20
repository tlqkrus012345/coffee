package com.coffee.domain.menu.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuDto {
    private String name;
    private int price;
}
