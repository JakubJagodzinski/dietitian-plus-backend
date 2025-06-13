package com.example.dietitian_plus.domain.mealsdishes.dto;

import com.example.dietitian_plus.domain.dishesproducts.dto.DishWithProductsResponseDto;
import com.example.dietitian_plus.domain.meal.dto.MealResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MealWithDishesResponseDto {

    private MealResponseDto meal;

    private List<DishWithProductsResponseDto> dishes;

}
