package com.coffee.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopularMenuDto {
    private Long menuId;
    private String menuName;
    private Long orderedCnt;
}
