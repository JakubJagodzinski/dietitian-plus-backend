package com.example.dietitian_plus.domain.dishesproducts.dto;

import com.example.dietitian_plus.domain.dishesproducts.DishProduct;
import com.example.dietitian_plus.domain.product.dto.ProductDtoMapper;
import com.example.dietitian_plus.domain.unit.dto.UnitDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DishProductDtoMapper {

    private final ProductDtoMapper productDtoMapper;
    private final UnitDtoMapper unitDtoMapper;

    public DishProductResponseDto toDto(DishProduct dishProduct) {
        DishProductResponseDto dto = new DishProductResponseDto();

        dto.setDishProductId(dishProduct.getDishProductId());
        dto.setProduct(productDtoMapper.toDto(dishProduct.getProduct()));
        dto.setUnit(unitDtoMapper.toDto(dishProduct.getUnit()));
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
