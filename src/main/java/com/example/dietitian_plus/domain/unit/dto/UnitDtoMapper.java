package com.example.dietitian_plus.domain.unit.dto;

import com.example.dietitian_plus.domain.unit.Unit;
import org.springframework.stereotype.Component;

@Component
public class UnitDtoMapper {

    public UnitResponseDto toDto(Unit unit) {
        UnitResponseDto dto = new UnitResponseDto();

        dto.setUnitId(unit.getUnitId());
        dto.setUnitName(unit.getUnitName());
        dto.setGrams(unit.getGrams());

        return dto;
    }

}
