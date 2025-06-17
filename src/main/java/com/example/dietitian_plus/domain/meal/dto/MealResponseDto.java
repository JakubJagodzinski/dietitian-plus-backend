package com.example.dietitian_plus.domain.meal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MealResponseDto {

    @JsonProperty("meal_id")
    private Long mealId;

    @JsonProperty("meal_name")
    private String mealName;

    private LocalDateTime datetime;

    @JsonProperty("nutrition_values")
    private NutritionValuesDto nutritionValues;

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("dietitian_id")
    private UUID dietitianId;

}
