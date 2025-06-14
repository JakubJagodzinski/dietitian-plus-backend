package com.example.dietitian_plus.domain.dishesproducts.dto;

import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DishWithProductsResponseDto {

    private DishResponseDto dish;

    private List<DishProductResponseDto> products;

}
