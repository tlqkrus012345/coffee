package com.coffee.api.menu.request;

import com.coffee.domain.menu.dto.BestMenuDto;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class PopularMenuRequest {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public static BestMenuDto from(PopularMenuRequest request) {
        BestMenuDto dto = new BestMenuDto();
        dto.setStartDateTime(request.getStartDateTime());
        dto.setEndDateTime(request.getEndDateTime());
        return dto;
    }
}
