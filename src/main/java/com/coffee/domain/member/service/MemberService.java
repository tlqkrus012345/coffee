package com.coffee.domain.member.service;

import com.coffee.common.redisson.aop.DistributedLock;
import com.coffee.domain.member.entity.Member;
import com.coffee.domain.member.entity.MemberRepository;
import com.coffee.domain.menu.dto.PointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @DistributedLock(key = "#key")
    //@Transactional
    public void chargePoint(PointDto pointDto) {
        Member member = memberRepository.findById(pointDto.getMemberId()).orElseThrow(IllegalArgumentException::new);
        member.chargePoint(pointDto.getPoint());
        memberRepository.save(member);
    }
}
