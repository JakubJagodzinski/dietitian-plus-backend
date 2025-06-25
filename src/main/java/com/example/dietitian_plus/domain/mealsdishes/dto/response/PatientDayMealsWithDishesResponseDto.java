package com.example.dietitian_plus.domain.mealsdishes.dto.response;

import com.example.dietitian_plus.domain.meal.dto.response.NutritionValuesResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PatientDayMealsWithDishesResponseDto {

    LocalDate date;

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("daily_nutrition_values")
    private NutritionValuesResponseDto dailyNutritionValues;

    private List<MealWithDishesResponseDto> meals;

}
