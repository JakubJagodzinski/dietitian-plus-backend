package com.example.dietitian_plus.dish.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DishResponseDto {

    private Long dishId;

    private Boolean isVisible;

    private Boolean isPublic;

    private Long dietitianId;

    private String dishName;

    private String recipe;

    private Float kcal;

    private Float fats;

    private Float carbs;

    private Float protein;

    private Float fiber;

}
