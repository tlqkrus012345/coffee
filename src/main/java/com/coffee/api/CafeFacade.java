package com.coffee.api;

import com.coffee.api.order.request.CreateOrderRequest;
import com.coffee.domain.menu.service.MenuService;
import com.coffee.domain.menu.dto.PopularMenuDto;
import com.coffee.domain.order.dto.CreateOrderDto;
import com.coffee.domain.order.service.OrderService;
import com.coffee.domain.payment.dto.PaymentDto;
import com.coffee.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CafeFacade {

    private final MenuService menuService;
    private final PaymentService paymentService;
    private final OrderService orderService;
    public List<PopularMenuDto> getPopularMenu(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        return menuService.getPopularMenu(startDate, endDate);
    }
    public PaymentDto orderAndPay(CreateOrderDto createOrderDto) {
        Long orderId = orderService.createOrder(createOrderDto).getOrderId();
        return paymentService.pay(orderId);
    }
}
