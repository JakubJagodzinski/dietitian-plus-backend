package com.example.dietitian_plus.meal;

import com.example.dietitian_plus.dish.dto.DishResponseDto;
import com.example.dietitian_plus.meal.dto.CreateMealRequestDto;
import com.example.dietitian_plus.meal.dto.MealResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/")
    public ResponseEntity<List<MealResponseDto>> getMeals() {
        return ResponseEntity.ok(mealService.getMeals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealResponseDto> getMealById(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @GetMapping("/{id}/dishes")
    public ResponseEntity<List<DishResponseDto>> getMealDishes(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.getMealDishes(id));
    }

    @PostMapping("/")
    public ResponseEntity<MealResponseDto> createMeal(@RequestBody CreateMealRequestDto createMealRequestDto) {
        MealResponseDto createdMeal = mealService.createMeal(createMealRequestDto);
        return ResponseEntity.created(URI.create("api/meals/" + createdMeal.getMealId())).body(createdMeal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealResponseDto> updateMealById(@PathVariable Long id, @RequestBody CreateMealRequestDto createMealRequestDto) {
        MealResponseDto updatedMeal = mealService.updateMealById(id, createMealRequestDto);
        return ResponseEntity.ok(updatedMeal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMealById(@PathVariable Long id) {
        mealService.deleteMealById(id);
        return ResponseEntity.ok("Meal deleted successfully");
    }

}
