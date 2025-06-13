package com.example.dietitian_plus.domain.mealsdishes.dto;

import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MealDishResponseDto {

    @JsonProperty("meal_id")
    private Long mealId;

    private DishResponseDto dish;

    @JsonProperty("dish_quantity")
    private Long dishQuantity;

}
