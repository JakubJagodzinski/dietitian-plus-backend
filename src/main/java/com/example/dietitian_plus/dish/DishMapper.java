package com.example.dietitian_plus.dish;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishMapper {

    public DishDto toDto(Dish dish) {
        DishDto dto = new DishDto();

        dto.setDishId(dish.getDishId());
        dto.setDishName(dish.getDishName());
        dto.setIsVisible(dish.getIsVisible());
        dto.setIsPublic(dish.getIsPublic());
        dto.setKcal(dish.getKcal());
        dto.setFats(dish.getFats());
        dto.setCarbs(dish.getCarbs());
        dto.setProtein(dish.getProtein());
        dto.setFiber(dish.getFiber());
        dto.setRecipe(dish.getRecipe());

        return dto;
    }

    public List<DishDto> toDtoList(List<Dish> dishes) {
        return dishes.stream().map(this::toDto).collect(Collectors.toList());
    }

}
