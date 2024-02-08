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
        Map<String, Integer> map = dto.getBestMenuList();
        List<String> keySet = new ArrayList<>(map.keySet());
        keySet.sort((o1, o2) -> {
            if (map.get(o1) == map.get(o2)) {
                return o1.compareTo(o2);
            }
            return map.get(o2).compareTo(map.get(o1));
        });

        return new BestMenuResponse(dto.getBestMenuList());
    }
}
