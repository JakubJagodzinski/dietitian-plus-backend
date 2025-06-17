package com.example.dietitian_plus.domain.dish.dto;

import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.dish.DishNutritionCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DishDtoMapper {

    private final DishNutritionCalculator dishNutritionCalculator;

    public DishResponseDto toDto(Dish dish) {
        DishResponseDto dto = new DishResponseDto();

        dto.setDishId(dish.getDishId());
        dto.setDishName(dish.getDishName());
        dto.setIsTemplate(dish.getIsTemplate());
        dto.setIsPublic(dish.getIsPublic());
        dto.setRecipe(dish.getRecipe());
        dto.setNutritionValues(dishNutritionCalculator.calculateDishNutritionValues(dish.getDishId()));
        dto.setDietitianId(dish.getDietitian().getUserId());

        return dto;
    }

    public List<DishResponseDto> toDtoList(List<Dish> dishes) {
        return dishes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
