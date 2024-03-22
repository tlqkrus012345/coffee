package com.coffee.domain.menu.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PopularMenuDto implements Serializable {
    private Long menuId;
    private String menuName;
    private Long orderedCnt;
}
