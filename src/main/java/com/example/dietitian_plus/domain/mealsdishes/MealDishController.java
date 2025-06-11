package com.example.dietitian_plus.domain.mealsdishes;

import com.example.dietitian_plus.auth.access.annotation.DietitianAccess;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.CreateMealDishRequestDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.MealDishResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MealDishController {

    private final MealDishService mealDishService;

    // TODO check ownership in access manager
    @GetMapping("/meals/{mealId}/dishes")
    public ResponseEntity<List<DishResponseDto>> getMealAllDishes(@PathVariable Long mealId) {
        List<DishResponseDto> dishResponseDtoList = mealDishService.getMealAllDishes(mealId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    // TODO check ownership in access manager
    @DietitianAccess
    @PostMapping("/meals/{mealId}/dishes")
    public ResponseEntity<MealDishResponseDto> addDishToMeal(@PathVariable Long mealId, @RequestBody CreateMealDishRequestDto createMealDishRequestDto) {
        MealDishResponseDto createdMealDishResponseDto = mealDishService.addDishToMeal(mealId, createMealDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/meals/" + createdMealDishResponseDto.getMealId() + "/dishes/" + createdMealDishResponseDto.getDishId()))
                .body(createdMealDishResponseDto);
    }

    // TODO check ownership in access manager
    @DietitianAccess
    @DeleteMapping("/meals/{mealId}/dishes/{dishId}")
    public ResponseEntity<MessageResponseDto> removeDishFromMeal(@PathVariable Long mealId, @PathVariable Long dishId) {
        mealDishService.removeDishFromMeal(mealId, dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dish with id " + dishId + " successfully removed from meal with id " + mealId));
    }

}
