package com.example.dietitian_plus.domain.mealsdishes;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.dish.dto.response.DishResponseDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.request.AddDishToMealRequestDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.response.MealDishResponseDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.response.MealWithDishesResponseDto;
import com.example.dietitian_plus.domain.mealsdishes.dto.response.PatientDayMealsWithDishesResponseDto;
import com.example.dietitian_plus.exception.ApiError;
import com.example.dietitian_plus.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class MealDishController {

    private final MealDishService mealDishService;

    @Operation(
            summary = "Get meal all dishes"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of meal all dishes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DishResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Meal not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.MEAL_DISH_READ_ALL)
    @GetMapping("/meals/{mealId}/dishes")
    public ResponseEntity<List<DishResponseDto>> getMealAllDishes(@PathVariable Long mealId) {
        List<DishResponseDto> dishResponseDtoList = mealDishService.getMealAllDishes(mealId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @Operation(
            summary = "Get meal all dishes with products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of meal all dishes with products",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MealWithDishesResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Meal not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.MEAL_DISH_READ_ALL)
    @GetMapping("/meals/{mealId}/dishes/products")
    public ResponseEntity<MealWithDishesResponseDto> getMealAllDishesWithProducts(@PathVariable Long mealId) {
        MealWithDishesResponseDto mealWithDishesResponseDto = mealDishService.getMealAllDishesWithProducts(mealId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealWithDishesResponseDto);
    }

    @Operation(
            summary = "Get patient day meals with dishes",
            parameters = {
                    @Parameter(
                            name = "date",
                            description = "Date to filter meals",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", format = "date", example = "2025-06-27")
                    ),
            }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of patient day meals with dishes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatientDayMealsWithDishesResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Patient not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_MEAL_READ_ALL)
    @GetMapping("/patients/{patientId}/meals/dishes")
    public ResponseEntity<PatientDayMealsWithDishesResponseDto> getPatientDayMealsWithDishes(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @PathVariable UUID patientId) {
        PatientDayMealsWithDishesResponseDto patientDayMealsWithDishesResponseDto = mealDishService.getPatientDayMealsWithDishes(date, patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientDayMealsWithDishesResponseDto);
    }

    @Operation(
            summary = "Add dish to meal"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Dish added to meal successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MealDishResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dish is already added to meal",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Meal / dish not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.MEAL_DISH_ADD)
    @PostMapping("/meals/{mealId}/dishes")
    public ResponseEntity<MealDishResponseDto> addDishToMeal(@PathVariable Long mealId, @Valid @RequestBody AddDishToMealRequestDto addDishToMealRequestDto) {
        MealDishResponseDto createdMealDishResponseDto = mealDishService.addDishToMeal(mealId, addDishToMealRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/meals/" + createdMealDishResponseDto.getMealId() + "/dishes/" + createdMealDishResponseDto.getDish().getDishId()))
                .body(createdMealDishResponseDto);
    }

    @Operation(
            summary = "Remove dish from meal"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Dish removed from meal successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dish is not added to meal",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Meal / dish not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.MEAL_DISH_REMOVE)
    @DeleteMapping("/meals/{mealId}/dishes/{dishId}")
    public ResponseEntity<Void> removeDishFromMeal(@PathVariable Long mealId, @PathVariable Long dishId) {
        mealDishService.removeDishFromMeal(mealId, dishId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
