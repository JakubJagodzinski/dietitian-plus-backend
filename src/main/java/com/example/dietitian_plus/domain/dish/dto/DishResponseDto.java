package com.example.dietitian_plus.domain.dish.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DishResponseDto {

    @JsonProperty("dish_id")
    private Long dishId;

    @JsonProperty("is_visible")
    private Boolean isVisible;

    @JsonProperty("is_public")
    private Boolean isPublic;

    @JsonProperty("dietitian_id")
    private Long dietitianId;

    @JsonProperty("dish_name")
    private String dishName;

    private String recipe;

    private Float kcal;

    private Float fats;

    private Float carbs;

    private Float protein;

    private Float fiber;

}
