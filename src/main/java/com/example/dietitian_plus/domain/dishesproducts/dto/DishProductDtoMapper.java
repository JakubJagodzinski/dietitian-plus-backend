package com.example.dietitian_plus.domain.dishesproducts.dto;

import com.example.dietitian_plus.domain.dishesproducts.DishProduct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishProductDtoMapper {

    public DishProductResponseDto toDto(DishProduct dishProduct) {
        DishProductResponseDto dto = new DishProductResponseDto();

        dto.setId(dishProduct.getId());
        dto.setDishId(dishProduct.getId());
        dto.setProductId(dishProduct.getProduct().getProductId());
        dto.setUnitId(dishProduct.getUnit().getUnitId());
        dto.setUnitCount(dishProduct.getUnitCount());

        return dto;
    }

    public List<DishProductResponseDto> toDtoList(List<DishProduct> dishProductList) {
        return dishProductList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
