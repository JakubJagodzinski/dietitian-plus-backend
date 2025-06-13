package com.example.dietitian_plus.domain.dietitian.dto;

import com.example.dietitian_plus.domain.dietitian.Dietitian;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DietitianDtoMapper {

    public DietitianResponseDto toDto(Dietitian dietitian) {
        DietitianResponseDto dto = new DietitianResponseDto();

        dto.setDietitianId(dietitian.getUserId());
        dto.setTitle(dietitian.getTitle());
        dto.setFirstName(dietitian.getFirstName());
        dto.setLastName(dietitian.getLastName());

        return dto;
    }

    public List<DietitianResponseDto> toDtoList(List<Dietitian> dietitians) {
        return dietitians.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
