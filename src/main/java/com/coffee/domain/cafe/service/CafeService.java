package com.coffee.domain.cafe.service;

import com.coffee.domain.cafe.dto.PointDto;
import com.coffee.domain.cafe.entity.CafeRepository;
import com.coffee.domain.cafe.dto.MenuDto;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;
    @Transactional(readOnly = true)
    public List<MenuDto> getMenu() {
        return cafeRepository.findAll().stream()
                .map(entity -> MenuDto.builder()
                        .name(entity.getName())
                        .price(entity.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
    @Transactional
    public void chargePoint(PointDto pointDto) {
        Member member = memberRepository.findById(pointDto.getMemberId()).orElseThrow();
        member.chargePoint(pointDto.getPoint());
        memberRepository.save(member);
    }
}
