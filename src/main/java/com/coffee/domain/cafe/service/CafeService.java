package com.coffee.domain.cafe.service;

import com.coffee.common.redisson.aop.DistributedLock;
import com.coffee.domain.cafe.dto.BestMenuDto;
import com.coffee.domain.cafe.dto.PointDto;
import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.dto.MenuDto;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.order.entity.Order;
import com.coffee.domain.order.entity.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<MenuDto> getMenu() {
        return cafeRepository.findAll().stream()
                .map(entity -> MenuDto.builder()
                        .name(entity.getName())
                        .price(entity.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
    @DistributedLock(key = "#key")
    //@Transactional
    public void chargePoint(PointDto pointDto) {
        Member member = memberRepository.findById(pointDto.getMemberId()).orElseThrow(IllegalArgumentException::new);
        member.chargePoint(pointDto.getPoint());
        memberRepository.save(member);
    }
    /*
        LocalDateTime 타입으로 시작과 끝을 입력 받는다
        지난 일주일 판매량 1 ~ 3등 메뉴를 반환하는 메소드
    */
    public BestMenuDto getBestWeekMenu(BestMenuDto bestMenuDto) {
        BestMenuDto result = new BestMenuDto();
        Map<String, Integer> bestMenuList = result.getBestMenuList();

        LocalDateTime startDateTime = bestMenuDto.getStartDateTime();
        LocalDateTime endDateTime = bestMenuDto.getEndDateTime();

        List<Order> byCreatedAtBetween = orderRepository.findByCreatedAtBetween(startDateTime, endDateTime);

        HashMap<String, Integer> map = new HashMap<>();
        for (Order order : byCreatedAtBetween) {
            String menuName = order.getMenuName();
            map.put(menuName, map.getOrDefault(menuName, 0) + 1);
        }

        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(map.entrySet());
        entryList.sort((o1, o2) -> o2.getValue() - o1.getValue());

        try {
            for (int i = 0; i < 3; i++) {
                bestMenuList.put(entryList.get(i).getKey(), entryList.get(i).getValue());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("not enough order");
        }

        result.setBestMenuList(bestMenuList);
        return result;
    }
}
