package com.example.dietitian_plus.domain.meal.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"meal_id", "patient_id", "dietitian_id", "meal_name", "datetime", "nutrition_values"})
public class MealResponseDto {

    @JsonProperty("meal_id")
    private Long mealId;

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("dietitian_id")
    private UUID dietitianId;

    @JsonProperty("meal_name")
    private String mealName;

    @JsonProperty("datetime")
    private LocalDateTime datetime;

    @JsonProperty("nutrition_values")
    private NutritionValuesResponseDto nutritionValues;

}
