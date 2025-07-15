package com.example.dietitian_plus.domain.meal;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.dto.ApiErrorResponseDto;
import com.example.dietitian_plus.domain.meal.dto.request.CreateMealRequestDto;
import com.example.dietitian_plus.domain.meal.dto.request.UpdateMealRequestDto;
import com.example.dietitian_plus.domain.meal.dto.response.MealResponseDto;
import com.example.dietitian_plus.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
public class MealController {

    private final MealService mealService;

    @Operation(
            summary = "Get all meals"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all meals",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MealResponseDto.class))
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
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.MEAL_READ_ALL)
    @GetMapping("/meals")
    public ResponseEntity<List<MealResponseDto>> getAllMeals() {
        List<MealResponseDto> mealResponseDtoList = mealService.getAllMeals();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDtoList);
    }

    @Operation(
            summary = "Get meal by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Meal found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MealResponseDto.class)
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
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Meal not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.MEAL_READ)
    @GetMapping("/meals/{mealId}")
    public ResponseEntity<MealResponseDto> getMealById(@PathVariable Long mealId) {
        MealResponseDto mealResponseDto = mealService.getMealById(mealId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDto);
    }

    @Operation(
            summary = "Get patient all meals",
            parameters = {
                    @Parameter(
                            name = "date",
                            description = "Optional date to filter meals",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", format = "date", example = "2025-06-27")
                    ),
            }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of patient all meals",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MealResponseDto.class))
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
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Patient not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_MEAL_READ_ALL)
    @GetMapping("/patients/{patientId}/meals")
    public ResponseEntity<List<MealResponseDto>> getPatientAllMeals(@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @PathVariable UUID patientId) {
        List<MealResponseDto> mealResponseDtoList = mealService.getPatientAllMeals(date, patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDtoList);
    }

    @Operation(
            summary = "Get dietitian all meals"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of dietitian all meals",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MealResponseDto.class))
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
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Dietitian not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.DIETITIAN_MEAL_READ_ALL)
    @GetMapping("/dietitians/{dietitianId}/meals")
    public ResponseEntity<List<MealResponseDto>> getDietitianAllMeals(@PathVariable UUID dietitianId) {
        List<MealResponseDto> mealResponseDtoList = mealService.getDietitianAllMeals(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mealResponseDtoList);
    }

    @Operation(
            summary = "Create meal"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Meal created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MealResponseDto.class)
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
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Dietitian / patient not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.MEAL_CREATE)
    @PostMapping("/meals")
    public ResponseEntity<MealResponseDto> createMeal(@Valid @RequestBody CreateMealRequestDto createMealRequestDto) {
        MealResponseDto createdMealResponseDto = mealService.createMeal(createMealRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/meals/" + createdMealResponseDto.getMealId()))
                .body(createdMealResponseDto);
    }

    @Operation(
            summary = "Update meal by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Meal updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MealResponseDto.class)
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
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Meal not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.MEAL_UPDATE)
    @PatchMapping("/meals/{mealId}")
    public ResponseEntity<MealResponseDto> updateMealById(@PathVariable Long mealId, @Valid @RequestBody UpdateMealRequestDto updateMealRequestDto) {
        MealResponseDto updatedMealResponseDto = mealService.updateMealById(mealId, updateMealRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedMealResponseDto);
    }

    @Operation(
            summary = "Delete meal by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Meal deleted successfully"
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
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Meal not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.MEAL_DELETE)
    @DeleteMapping("/meals/{mealId}")
    public ResponseEntity<Void> deleteMealById(@PathVariable Long mealId) {
        mealService.deleteMealById(mealId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
