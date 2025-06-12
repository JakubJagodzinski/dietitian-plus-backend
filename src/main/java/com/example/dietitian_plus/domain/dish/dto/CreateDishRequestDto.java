package com.example.dietitian_plus.domain.dish.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateDishRequestDto {

    @JsonProperty("dish_name")
    private String dishName;

    @JsonProperty("is_template")
    private Boolean isTemplate;

    @JsonProperty("is_public")
    private Boolean isPublic;

    private String recipe;

    private Float kcal;

    private Float fats;

    private Float carbs;

    private Float protein;

    private Float fiber;

    @JsonProperty("dietitian_id")
    private Long dietitianId;

}
