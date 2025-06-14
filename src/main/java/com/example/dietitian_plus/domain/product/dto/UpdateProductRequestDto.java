package com.example.dietitian_plus.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductRequestDto {

    @JsonProperty("product_name")
    private String productName;

    private Float kcal;

    private Float fats;

    private Float carbs;

    private Float protein;

    private Float fiber;

    @JsonProperty("glycemic_index")
    private Float glycemicIndex;

    @JsonProperty("glycemic_load")
    private Float glycemicLoad;

}
