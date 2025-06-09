package com.example.dietitian_plus.domain.mealsdishes.dto;

import com.example.dietitian_plus.domain.mealsdishes.MealDish;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MealDishDtoMapper {


    public MealDishResponseDto toDto(MealDish mealDish) {
        MealDishResponseDto dto = new MealDishResponseDto();

        dto.setMealId(mealDish.getMeal().getMealId());
        dto.setDishId(mealDish.getDish().getDishId());
        dto.setDishQuantity(mealDish.getDishQuantity());

        return dto;
    }

    public List<MealDishResponseDto> toDtoList(List<MealDish> mealDishList) {
        return mealDishList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
