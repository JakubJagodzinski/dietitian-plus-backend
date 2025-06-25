package com.example.dietitian_plus.domain.mealsdishes;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.dish.dto.response.DishResponseDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.request.CreateMealDishRequestDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.response.MealDishResponseDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.response.MealWithDishesResponseDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.response.PatientDayMealsWithDishesResponseDto;
import com.example.dietitian_plus.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MealDishController {

    private final MealDishService mealDishService;

    @CheckPermission(Permission.MEAL_DISH_READ_ALL)
    @GetMapping("/meals/{mealId}/dishes")
    public ResponseEntity<List<DishResponseDto>> getMealAllDishes(@PathVariable Long mealId) {
        List<DishResponseDto> dishResponseDtoList = mealDishService.getMealAllDishes(mealId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @CheckPermission(Permission.MEAL_DISH_READ_ALL)
    @GetMapping("/meals/{mealId}/dishes/products")
    public ResponseEntity<MealWithDishesResponseDto> getMealAllDishesWithProducts(@PathVariable Long mealId) {
        MealWithDishesResponseDto mealWithDishesResponseDto = mealDishService.getMealAllDishesWithProducts(mealId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealWithDishesResponseDto);
    }

    @CheckPermission(Permission.PATIENT_MEAL_READ_ALL)
    @GetMapping("/patients/{patientId}/meals/dishes")
    public ResponseEntity<PatientDayMealsWithDishesResponseDto> getPatientDayMealsWithDishes(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @PathVariable UUID patientId) {
        PatientDayMealsWithDishesResponseDto patientDayMealsWithDishesResponseDto = mealDishService.getPatientDayMeals(date, patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientDayMealsWithDishesResponseDto);
    }

    @CheckPermission(Permission.MEAL_DISH_ADD)
    @PostMapping("/meals/{mealId}/dishes")
    public ResponseEntity<MealDishResponseDto> addDishToMeal(@PathVariable Long mealId, @RequestBody CreateMealDishRequestDto createMealDishRequestDto) {
        MealDishResponseDto createdMealDishResponseDto = mealDishService.addDishToMeal(mealId, createMealDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/meals/" + createdMealDishResponseDto.getMealId() + "/dishes/" + createdMealDishResponseDto.getDish().getDishId()))
                .body(createdMealDishResponseDto);
    }

    @CheckPermission(Permission.MEAL_DISH_REMOVE)
    @DeleteMapping("/meals/{mealId}/dishes/{dishId}")
    public ResponseEntity<MessageResponseDto> removeDishFromMeal(@PathVariable Long mealId, @PathVariable Long dishId) {
        mealDishService.removeDishFromMeal(mealId, dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dish with id " + dishId + " successfully removed from meal with id " + mealId));
    }

}
