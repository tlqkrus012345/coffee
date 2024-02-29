package com.coffee.api.cafe.response;

import com.coffee.domain.cafe.dto.BestMenuDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public class BestMenuResponse {
    private Map<String, Integer> bestMenuList;

    public static BestMenuResponse from(BestMenuDto dto) {
        return new BestMenuResponse(dto.getBestMenuList());
    }
}
