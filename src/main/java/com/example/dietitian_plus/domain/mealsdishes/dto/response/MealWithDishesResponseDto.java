package com.example.dietitian_plus.domain.mealsdishes.dto.response;

import com.example.dietitian_plus.domain.dishesproducts.dto.response.DishWithProductsResponseDto;
import com.example.dietitian_plus.domain.meal.dto.response.MealResponseDto;
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
