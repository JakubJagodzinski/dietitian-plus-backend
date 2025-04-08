package com.example.dietitian_plus.product;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setKcal(product.getKcal());
        dto.setFats(product.getFats());
        dto.setCarbs(product.getCarbs());
        dto.setProtein(product.getProtein());
        dto.setFiber(product.getFiber());

        return dto;
    }

    public List<ProductDto> toDtoList(List<Product> products) {
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }

}
