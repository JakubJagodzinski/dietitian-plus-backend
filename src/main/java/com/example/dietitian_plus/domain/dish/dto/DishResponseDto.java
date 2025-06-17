package com.example.dietitian_plus.domain.dish.dto;

import com.example.dietitian_plus.domain.meal.dto.NutritionValuesDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DishResponseDto {

    @JsonProperty("dish_id")
    private Long dishId;

    @JsonProperty("dish_name")
    private String dishName;

    @JsonProperty("is_template")
    private Boolean isTemplate;

    @JsonProperty("is_public")
    private Boolean isPublic;

    private String recipe;

    @JsonProperty("nutrition_values")
    private NutritionValuesDto nutritionValues;

    @JsonProperty("dietitian_id")
    private UUID dietitianId;

}
