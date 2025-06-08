package com.example.dietitian_plus.domain.mealsdishes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateMealDishRequestDto {

    @JsonProperty("dish_id")
    private Long dishId;

}
