package com.example.dietitian_plus.meal;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MealMapper {


    public MealDto toDto(Meal meal) {
        MealDto dto = new MealDto();

        dto.setMealId(meal.getMealId());
        dto.setDatetime(meal.getDatetime());
        dto.setPatientId(meal.getPatient().getPatientId());
        dto.setDietitianId(meal.getDietitian().getDietitianId());

        return dto;
    }

    public List<MealDto> toDtoList(List<Meal> meals) {
        return meals.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
