package com.coffee.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private int point;
//    @Version
//    private Long version;
    public void chargePoint(int point) {
        this.point += point;
    }
    public void usePoint(int point) {
        this.point -= point;
    }
}
