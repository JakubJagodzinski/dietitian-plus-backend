package com.example.dietitian_plus.domain.product.dto;

import com.example.dietitian_plus.domain.meal.dto.response.NutritionValuesResponseDto;
import com.example.dietitian_plus.domain.product.Product;
import com.example.dietitian_plus.domain.product.dto.response.ProductResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {

    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        NutritionValuesResponseDto nutritionValues = new NutritionValuesResponseDto();

        nutritionValues.setKcal(product.getKcal());
        nutritionValues.setFats(product.getFats());
        nutritionValues.setCarbs(product.getCarbs());
        nutritionValues.setProtein(product.getProtein());
        nutritionValues.setFiber(product.getFiber());
        nutritionValues.setGlycemicIndex(product.getGlycemicIndex());
        nutritionValues.setGlycemicLoad(product.getGlycemicLoad());

        dto.setNutritionValues(nutritionValues);

        return dto;
    }

    public List<ProductResponseDto> toDtoList(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
