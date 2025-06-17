package com.example.dietitian_plus.domain.meal.dto;

import com.example.dietitian_plus.domain.meal.Meal;
import com.example.dietitian_plus.domain.meal.MealNutritionCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MealDtoMapper {

    private final MealNutritionCalculator mealNutritionCalculator;

    public MealResponseDto toDto(Meal meal) {
        MealResponseDto dto = new MealResponseDto();

        dto.setMealId(meal.getMealId());
        dto.setMealName(meal.getMealName());
        dto.setDatetime(meal.getDatetime());
        dto.setNutritionValues(mealNutritionCalculator.calculateMealNutritionValues(meal));
        dto.setPatientId(meal.getPatient().getUserId());
        dto.setDietitianId(meal.getDietitian().getUserId());

        return dto;
    }

    public List<MealResponseDto> toDtoList(List<Meal> meals) {
        return meals.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
