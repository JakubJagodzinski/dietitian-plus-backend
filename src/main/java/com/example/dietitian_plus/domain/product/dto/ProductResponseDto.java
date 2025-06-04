package com.example.dietitian_plus.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto {

    private Long productId;

    private String productName;

    private Float kcal;

    private Float fats;

    private Float carbs;

    private Float protein;

    private Float fiber;

}
