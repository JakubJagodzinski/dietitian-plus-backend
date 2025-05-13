package com.example.dietitian_plus.meal;

import com.example.dietitian_plus.dish.DishDto;
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
    public ResponseEntity<List<MealDto>> getMeals() {
        return ResponseEntity.ok(mealService.getMeals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDto> getMealById(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @GetMapping("/{id}/dishes")
    public ResponseEntity<List<DishDto>> getMealDishes(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.getMealDishes(id));
    }

    @PostMapping("/")
    public ResponseEntity<MealDto> createMeal(@RequestBody CreateMealDto createMealDto) {
        MealDto createdMeal = mealService.createMeal(createMealDto);
        return ResponseEntity.created(URI.create("api/meals/" + createdMeal.getMealId())).body(createdMeal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealDto> updateMealById(@PathVariable Long id, @RequestBody CreateMealDto createMealDto) {
        MealDto updatedMeal = mealService.updateMealById(id, createMealDto);
        return ResponseEntity.ok(updatedMeal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMealById(@PathVariable Long id) {
        mealService.deleteMealById(id);
        return ResponseEntity.ok("Meal deleted successfully");
    }

}
