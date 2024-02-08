package com.coffee.api.cafe.request;

import com.coffee.domain.cafe.dto.BestMenuDto;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class BestMenuRequest {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public static BestMenuDto from(BestMenuRequest request) {
        BestMenuDto dto = new BestMenuDto();
        dto.setStartDateTime(request.getStartDateTime());
        dto.setEndDateTime(request.getEndDateTime());
        return dto;
    }
}
