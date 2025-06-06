package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.meal.dto.CreateMealRequestDto;
import com.example.dietitian_plus.domain.meal.dto.MealResponseDto;
import com.example.dietitian_plus.domain.meal.dto.UpdateMealRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping
    public ResponseEntity<List<MealResponseDto>> getMeals() {
        List<MealResponseDto> mealResponseDtoList = mealService.getMeals();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDtoList);
    }

    @GetMapping("/{mealId}")
    public ResponseEntity<MealResponseDto> getMealById(@PathVariable Long mealId) {
        MealResponseDto mealResponseDto = mealService.getMealById(mealId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDto);
    }

    @PostMapping
    public ResponseEntity<MealResponseDto> createMeal(@RequestBody CreateMealRequestDto createMealRequestDto) {
        MealResponseDto createdMealResponseDto = mealService.createMeal(createMealRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/meals/" + createdMealResponseDto.getMealId()))
                .body(createdMealResponseDto);
    }

    @PutMapping("/{mealId}")
    public ResponseEntity<MealResponseDto> updateMealById(@PathVariable Long mealId, @RequestBody UpdateMealRequestDto updateMealRequestDto) {
        MealResponseDto updatedMealResponseDto = mealService.updateMealById(mealId, updateMealRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedMealResponseDto);
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<MessageResponseDto> deleteMealById(@PathVariable Long mealId) {
        mealService.deleteMealById(mealId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Meal with id " + mealId + " deleted successfully"));
    }

}
