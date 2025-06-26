package com.example.dietitian_plus.domain.dishesproducts.dto.response;

import com.example.dietitian_plus.domain.dish.dto.response.DishResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"dish", "products"})
public class DishWithProductsResponseDto {

    @JsonProperty("dish")
    private DishResponseDto dish;

    @JsonProperty("products")
    private List<DishProductResponseDto> products;

}
