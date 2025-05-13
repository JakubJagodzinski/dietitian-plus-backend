package com.example.dietitian_plus.meal.dto;

import com.example.dietitian_plus.meal.Meal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MealDtoMapper {


    public MealResponseDto toDto(Meal meal) {
        MealResponseDto dto = new MealResponseDto();

        dto.setMealId(meal.getMealId());
        dto.setDatetime(meal.getDatetime());
        dto.setPatientId(meal.getPatient().getPatientId());
        dto.setDietitianId(meal.getDietitian().getDietitianId());

        return dto;
    }

    public List<MealResponseDto> toDtoList(List<Meal> meals) {
        return meals.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
