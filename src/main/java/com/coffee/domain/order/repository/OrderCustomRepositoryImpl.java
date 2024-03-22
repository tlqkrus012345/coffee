package com.coffee.domain.order.repository;

import com.coffee.domain.menu.dto.PopularMenuDto;
import com.coffee.domain.order.entity.QOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final QOrder order = QOrder.order;

    @Override
    public List<PopularMenuDto> findPopularMenu(LocalDate start, LocalDate end) {
        return jpaQueryFactory
                .select(Projections.bean(PopularMenuDto.class,
                        order.menuId,
                        order.menuName,
                        order.menuId.count().as("orderedCnt")))
                .from(order)
                .where(
                        new BooleanBuilder()
                                .and(order.createdAt.goe(start.atStartOfDay()))
                                .and(order.createdAt.lt(end.atStartOfDay()))
                )
                .groupBy(order.menuId, order.menuName)
                .orderBy(order.menuId.count().desc())
                .limit(3)
                .fetch();
    };
}
