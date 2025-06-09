package com.example.dietitian_plus.domain.dish.dto;

import com.example.dietitian_plus.domain.dish.Dish;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishDtoMapper {

    public DishResponseDto toDto(Dish dish) {
        DishResponseDto dto = new DishResponseDto();

        dto.setDishId(dish.getDishId());
        dto.setDishName(dish.getDishName());
        dto.setIsVisible(dish.getIsVisible());
        dto.setIsPublic(dish.getIsPublic());
        dto.setDietitianId(dish.getDietitian().getId());
        dto.setKcal(dish.getKcal());
        dto.setFats(dish.getFats());
        dto.setCarbs(dish.getCarbs());
        dto.setProtein(dish.getProtein());
        dto.setFiber(dish.getFiber());
        dto.setRecipe(dish.getRecipe());

        return dto;
    }

    public List<DishResponseDto> toDtoList(List<Dish> dishes) {
        return dishes.stream().map(this::toDto).collect(Collectors.toList());
    }

}
