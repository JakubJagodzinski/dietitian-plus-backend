package com.example.dietitian_plus.dietitian;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DietitianMapper {

    public DietitianDto toDto(Dietitian dietitian) {
        DietitianDto dto = new DietitianDto();

        dto.setDietitianId(dietitian.getDietitianId());
        dto.setEmail(dietitian.getEmail());
        dto.setPassword(dietitian.getPassword());
        dto.setTitle(dietitian.getTitle());
        dto.setFirstName(dietitian.getFirstName());
        dto.setLastName(dietitian.getLastName());

        return dto;
    }

    public List<DietitianDto> toDtoList(List<Dietitian> dietitians) {
        return dietitians.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
