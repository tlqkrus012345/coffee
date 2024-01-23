package com.coffee.domain.cafe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Menu, Long> {
}
