package com.example.dietitian_plus.domain.mealsdishes.dto.response;

import com.example.dietitian_plus.domain.dish.dto.response.DishResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"meal_id", "dish", "dish_quantity"})
public class MealDishResponseDto {

    @JsonProperty("meal_id")
    private Long mealId;

    @JsonProperty("dish")
    private DishResponseDto dish;

    @JsonProperty("dish_quantity")
    private Integer dishQuantity;

}
