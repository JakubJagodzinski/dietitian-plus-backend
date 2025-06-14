package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.meal.dto.CreateMealRequestDto;
import com.example.dietitian_plus.domain.meal.dto.MealResponseDto;
import com.example.dietitian_plus.domain.meal.dto.UpdateMealRequestDto;
import com.example.dietitian_plus.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @CheckPermission(Permission.MEAL_READ_ALL)
    @GetMapping("/meals")
    public ResponseEntity<List<MealResponseDto>> getAllMeals() {
        List<MealResponseDto> mealResponseDtoList = mealService.getAllMeals();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDtoList);
    }

    @CheckPermission(Permission.MEAL_READ)
    @GetMapping("/meals/{mealId}")
    public ResponseEntity<MealResponseDto> getMealById(@PathVariable Long mealId) {
        MealResponseDto mealResponseDto = mealService.getMealById(mealId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDto);
    }

    @CheckPermission(Permission.PATIENT_MEAL_READ_ALL)
    @GetMapping("/patients/{patientId}/meals")
    public ResponseEntity<List<MealResponseDto>> getPatientAllMeals(@PathVariable UUID patientId) {
        List<MealResponseDto> mealResponseDtoList = mealService.getPatientAllMeals(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDtoList);
    }

    @CheckPermission(Permission.DIETITIAN_MEAL_READ_ALL)
    @GetMapping("/dietitians/{dietitianId}/meals")
    public ResponseEntity<List<MealResponseDto>> getDietitianAllMeals(@PathVariable UUID dietitianId) {
        List<MealResponseDto> mealResponseDtoList = mealService.getDietitianAllMeals(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDtoList);
    }

    @CheckPermission(Permission.MEAL_CREATE)
    @PostMapping("/meals")
    public ResponseEntity<MealResponseDto> createMeal(@RequestBody CreateMealRequestDto createMealRequestDto) {
        MealResponseDto createdMealResponseDto = mealService.createMeal(createMealRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/meals/" + createdMealResponseDto.getMealId()))
                .body(createdMealResponseDto);
    }

    @CheckPermission(Permission.MEAL_UPDATE)
    @PutMapping("/meals/{mealId}")
    public ResponseEntity<MealResponseDto> updateMealById(@PathVariable Long mealId, @RequestBody UpdateMealRequestDto updateMealRequestDto) {
        MealResponseDto updatedMealResponseDto = mealService.updateMealById(mealId, updateMealRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedMealResponseDto);
    }

    @CheckPermission(Permission.MEAL_DELETE)
    @DeleteMapping("/meals/{mealId}")
    public ResponseEntity<MessageResponseDto> deleteMealById(@PathVariable Long mealId) {
        mealService.deleteMealById(mealId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Meal with id " + mealId + " deleted successfully"));
    }

}
