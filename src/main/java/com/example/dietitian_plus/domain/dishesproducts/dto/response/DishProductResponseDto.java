package com.example.dietitian_plus.domain.dishesproducts.dto.response;

import com.example.dietitian_plus.domain.product.dto.response.ProductResponseDto;
import com.example.dietitian_plus.domain.unit.dto.response.UnitResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"dish_product_id", "dish_id", "product", "unit", "unit_count"})
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
