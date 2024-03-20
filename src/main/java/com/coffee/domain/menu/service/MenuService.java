package com.coffee.domain.menu.service;

import com.coffee.common.redisson.aop.DistributedLock;
import com.coffee.domain.menu.dto.BestMenuDto;
import com.coffee.domain.menu.dto.PointDto;
import com.coffee.domain.menu.entity.MenuRepository;
import com.coffee.domain.menu.dto.MenuDto;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<MenuDto> getMenu() {
        return menuRepository.findAll().stream()
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
    @Transactional(readOnly = true)
    public BestMenuDto getBestWeekMenu(BestMenuDto bestMenuDto) {
        BestMenuDto result = new BestMenuDto();
        Map<String, Integer> bestMenuList = result.getBestMenuList();

        LocalDateTime startDateTime = bestMenuDto.getStartDateTime();
        LocalDateTime endDateTime = bestMenuDto.getEndDateTime();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<String> byCreatedAtBetween = orderRepository.findByCreatedAtBetween(startDateTime, endDateTime);

        HashMap<String, Integer> map = new HashMap<>();
        for (String menuName : byCreatedAtBetween) {
            map.put(menuName, map.getOrDefault(menuName, 0) + 1);
        }

        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(map.entrySet());
        entryList.sort((o1, o2) -> {
            if (o1.getValue() == o2.getValue()) {
                return o1.getKey().compareTo(o2.getKey());
            }
            return o2.getValue() - o1.getValue();
        });

        try {
            for (int i = 0; i < 3; i++) {
                bestMenuList.put(entryList.get(i).getKey(), entryList.get(i).getValue());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("not enough order");
        }

        result.setBestMenuList(bestMenuList);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        return result;
    }
}
