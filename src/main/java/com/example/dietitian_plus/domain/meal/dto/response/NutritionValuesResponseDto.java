package com.example.dietitian_plus.domain.meal.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"kcal", "fats", "carbs", "protein", "fiber", "glycemic_index", "glycemic_load"})
public class NutritionValuesResponseDto {

    private Double kcal;

    private Double fats;

    private Double carbs;

    private Double protein;

    private Double fiber;

    @JsonProperty("glycemic_index")
    private Double glycemicIndex;

    @JsonProperty("glycemic_load")
    private Double glycemicLoad;

}
