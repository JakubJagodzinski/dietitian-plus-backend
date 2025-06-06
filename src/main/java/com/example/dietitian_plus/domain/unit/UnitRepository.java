package com.example.dietitian_plus.domain.unit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    boolean existsByUnitName(String unitName);

    Optional<Unit> findByUnitName(String unitName);

}
