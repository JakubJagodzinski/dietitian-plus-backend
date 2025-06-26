package com.example.dietitian_plus.domain.mealsdishes.dto.response;

import com.example.dietitian_plus.domain.dishesproducts.dto.response.DishWithProductsResponseDto;
import com.example.dietitian_plus.domain.meal.dto.response.MealResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"meal", "dishes"})
public class MealWithDishesResponseDto {

    @JsonProperty("meal")
    private MealResponseDto meal;

    @JsonProperty("dishes")
    private List<DishWithProductsResponseDto> dishes;

}
