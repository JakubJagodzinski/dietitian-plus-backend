package com.example.dietitian_plus.product;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setProductName(product.getProductName());
        dto.setKcal(product.getKcal());
        dto.setFats(product.getFats());
        dto.setCarbs(product.getCarbs());
        dto.setProtein(product.getProtein());
        dto.setFiber(product.getFiber());

        return dto;
    }

}
