package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.meal.dto.request.CreateMealRequestDto;
import com.example.dietitian_plus.domain.meal.dto.request.UpdateMealRequestDto;
import com.example.dietitian_plus.domain.meal.dto.response.MealResponseDto;
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
    public ResponseEntity<List<MealResponseDto>> getPatientAllMeals(@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @PathVariable UUID patientId) {
        List<MealResponseDto> mealResponseDtoList = mealService.getPatientAllMeals(date, patientId);

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
    @PatchMapping("/meals/{mealId}")
    public ResponseEntity<MealResponseDto> updateMealById(@PathVariable Long mealId, @RequestBody UpdateMealRequestDto updateMealRequestDto) {
        MealResponseDto updatedMealResponseDto = mealService.updateMealById(mealId, updateMealRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedMealResponseDto);
    }

    @CheckPermission(Permission.MEAL_DELETE)
    @DeleteMapping("/meals/{mealId}")
    public ResponseEntity<Void> deleteMealById(@PathVariable Long mealId) {
        mealService.deleteMealById(mealId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
