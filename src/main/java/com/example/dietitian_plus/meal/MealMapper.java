package com.example.dietitian_plus.meal;

import org.springframework.stereotype.Component;

@Component
public class MealMapper {


    public MealDto toDto(Meal meal) {
        MealDto dto = new MealDto();

        dto.setDatetime(meal.getDatetime());
        dto.setUserId(meal.getUser().getUserId());
        dto.setDietitianId(meal.getDietitian().getDietitianId());

        return dto;
    }

}
