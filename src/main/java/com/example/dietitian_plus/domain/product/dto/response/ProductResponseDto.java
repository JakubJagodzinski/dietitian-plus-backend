package com.example.dietitian_plus.domain.product.dto.response;

import com.example.dietitian_plus.domain.meal.dto.response.NutritionValuesResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"product_id", "product_name", "nutrition_values"})
public class ProductResponseDto {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("nutrition_values")
    private NutritionValuesResponseDto nutritionValues;

}
