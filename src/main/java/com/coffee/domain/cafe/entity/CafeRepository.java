package com.coffee.domain.cafe.entity;

import com.coffee.domain.cafe.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Menu, Long> {
}
