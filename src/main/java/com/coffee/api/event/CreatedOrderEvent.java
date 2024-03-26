package com.coffee.api.event;

import com.coffee.domain.order.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatedOrderEvent {

    private OrderDto orderDto;
}
