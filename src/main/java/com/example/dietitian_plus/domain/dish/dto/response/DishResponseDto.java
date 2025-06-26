package com.example.dietitian_plus.domain.dish.dto.response;

import com.example.dietitian_plus.domain.meal.dto.response.NutritionValuesResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"dish_id", "dietitian_id", "dish_name", "is_template", "is_public", "recipe", "nutrition_values"})
public class DishResponseDto {

    @JsonProperty("dish_id")
    private Long dishId;

    @JsonProperty("dietitian_id")
    private UUID dietitianId;

    @JsonProperty("dish_name")
    private String dishName;

    @JsonProperty("is_template")
    private boolean isTemplate;

    @JsonProperty("is_public")
    private boolean isPublic;

    @JsonProperty("recipe")
    private String recipe;

    @JsonProperty("nutrition_values")
    private NutritionValuesResponseDto nutritionValues;

}
