package com.coffee.domain.member.entity;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
//    @Override
//    @Transactional
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("select m from Member m where m.id = :id")
//    Optional<Member> findById(@Param(value = "id") Long id);
}
