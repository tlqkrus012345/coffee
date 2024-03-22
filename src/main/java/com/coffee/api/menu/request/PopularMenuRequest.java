package com.coffee.api.menu.request;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class PopularMenuRequest {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
