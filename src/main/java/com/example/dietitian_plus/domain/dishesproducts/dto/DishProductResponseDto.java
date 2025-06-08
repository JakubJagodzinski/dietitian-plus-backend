package com.example.dietitian_plus.domain.dishesproducts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DishProductResponseDto {

    private Long id;

    @JsonProperty("dish_id")
    private Long dishId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("unit_id")
    private Long unitId;

    @JsonProperty("unit_count")
    private Float unitCount;

}
