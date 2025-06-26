package com.example.dietitian_plus.domain.dishesproducts.dto.response;

import com.example.dietitian_plus.domain.dish.dto.response.DishResponseDto;
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

    private DishResponseDto dish;

    private List<DishProductResponseDto> products;

}
