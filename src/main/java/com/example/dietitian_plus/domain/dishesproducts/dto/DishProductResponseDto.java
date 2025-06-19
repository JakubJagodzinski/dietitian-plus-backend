package com.example.dietitian_plus.domain.dishesproducts.dto;

import com.example.dietitian_plus.domain.product.dto.ProductResponseDto;
import com.example.dietitian_plus.domain.unit.dto.UnitResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DishProductResponseDto {

    @JsonProperty("dish_product_id")
    private Long dishProductId;

    @JsonProperty("dish_id")
    private Long dishId;

    @JsonProperty("product")
    private ProductResponseDto product;

    @JsonProperty("unit")
    private UnitResponseDto unit;

    @JsonProperty("unit_count")
    private Double unitCount;

}
