package com.coffee.api.member;

import com.coffee.api.member.request.ChargePointRequest;
import com.coffee.domain.member.service.MemberService;
import com.coffee.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/point")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void chargePoint(@RequestBody ChargePointRequest chargePointRequest) {
        memberService.chargePoint(ChargePointRequest.from(chargePointRequest));
    }
}
