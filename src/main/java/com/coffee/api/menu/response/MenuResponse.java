package com.coffee.api.menu.response;

import com.coffee.domain.menu.dto.MenuDto;
import lombok.Getter;

@Getter
public class MenuResponse {
    private String name;
    private int price;
    public MenuResponse(String name, int price) {
        this.name = name;
        this.price = price;
    }
    public static MenuResponse from(MenuDto menuDto) {
       return new MenuResponse(menuDto.getName(), menuDto.getPrice());
    }
}
