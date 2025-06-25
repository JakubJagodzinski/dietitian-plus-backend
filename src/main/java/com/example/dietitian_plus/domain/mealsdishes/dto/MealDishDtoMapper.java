package com.example.dietitian_plus.domain.mealsdishes.dto;

import com.example.dietitian_plus.domain.dish.dto.DishDtoMapper;
import com.example.dietitian_plus.domain.mealsdishes.MealDish;
import com.example.dietitian_plus.domain.mealsdishes.dto.response.MealDishResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MealDishDtoMapper {

    private final DishDtoMapper dishDtoMapper;

    public MealDishResponseDto toDto(MealDish mealDish) {
        MealDishResponseDto dto = new MealDishResponseDto();

        dto.setMealId(mealDish.getMeal().getMealId());
        dto.setDish(dishDtoMapper.toDto(mealDish.getDish()));
        dto.setDishQuantity(mealDish.getDishQuantity());

        return dto;
    }

    public List<MealDishResponseDto> toDtoList(List<MealDish> mealDishList) {
        return mealDishList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
