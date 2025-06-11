package com.example.dietitian_plus.domain.product.dto;

import com.example.dietitian_plus.domain.product.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {

    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setKcal(product.getKcal());
        dto.setFats(product.getFats());
        dto.setCarbs(product.getCarbs());
        dto.setProtein(product.getProtein());
        dto.setFiber(product.getFiber());
        dto.setGlycemicIndex(product.getGlycemicIndex());
        dto.setGlycemicLoad(product.getGlycemicLoad());

        return dto;
    }

    public List<ProductResponseDto> toDtoList(List<Product> products) {
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }

}
