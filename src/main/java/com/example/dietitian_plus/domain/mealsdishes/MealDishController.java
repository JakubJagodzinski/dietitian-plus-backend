package com.example.dietitian_plus.domain.mealsdishes;

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
@RequestMapping("/api/v1/meals-dishes")
@RequiredArgsConstructor
public class MealDishController {

    private final MealDishService mealDishService;

    @GetMapping("/{mealId}")
    public ResponseEntity<List<DishResponseDto>> getMealDishesByMealId(@PathVariable Long mealId) {
        List<DishResponseDto> dishResponseDtoList = mealDishService.getMealDishesByMealId(mealId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<MealDishResponseDto> createMealDish(@RequestBody CreateMealDishRequestDto createMealDishRequestDto) {
        MealDishResponseDto createdMealDishResponseDto = mealDishService.createMealDish(createMealDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/meals-dishes/" + createdMealDishResponseDto.getMealId() + "/" + createdMealDishResponseDto.getDishId()))
                .body(createdMealDishResponseDto);
    }

    @DeleteMapping("/{mealDishId}")
    public ResponseEntity<MessageResponseDto> deleteMealDishById(@PathVariable Long mealDishId) {
        mealDishService.deleteMealDishById(mealDishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Meal dish with id " + mealDishId + " deleted successfully"));
    }

}
