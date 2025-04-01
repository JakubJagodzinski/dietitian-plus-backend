package com.example.dietitian_plus.unit;

import org.springframework.stereotype.Component;

@Component
public class UnitMapper {

    public UnitDto toDto(Unit unit) {
        UnitDto dto = new UnitDto();

        dto.setUnitId(unit.getUnitId());
        dto.setUnitName(unit.getUnitName());
        dto.setGrams(unit.getGrams());

        return dto;
    }

}
