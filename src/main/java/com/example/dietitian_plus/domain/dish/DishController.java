package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.dish.dto.request.CreateDishRequestDto;
import com.example.dietitian_plus.domain.dish.dto.request.UpdateDishRequestDto;
import com.example.dietitian_plus.domain.dish.dto.response.DishResponseDto;
import com.example.dietitian_plus.exception.ApiError;
import com.example.dietitian_plus.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class DishController {

    private final DishService dishService;

    @Operation(
            summary = "Get all dishes"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all dishes",
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
            )
    })
    @CheckPermission(Permission.DISH_READ_ALL)
    @GetMapping("/dishes")
    public ResponseEntity<List<DishResponseDto>> getAllDishes() {
        List<DishResponseDto> dishResponseDtoList = dishService.getAllDishes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @Operation(
            summary = "Get all public dishes"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all public dishes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DishResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            )
    })
    @GetMapping("/dishes/public")
    public ResponseEntity<List<DishResponseDto>> getAllPublicDishes() {
        List<DishResponseDto> dishResponseDtoList = dishService.getAllPublicDishes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @Operation(
            summary = "Get dish by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dish found",
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
                    description = "Dish not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DISH_READ)
    @GetMapping("/dishes/{dishId}")
    public ResponseEntity<DishResponseDto> getDishById(@PathVariable Long dishId) {
        DishResponseDto dishResponseDto = dishService.getDishById(dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDto);
    }

    @Operation(
            summary = "Get dietitian all dishes"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of dietitian all dishes",
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
                    description = "Dietitian not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DIETITIAN_DISH_READ_ALL)
    @GetMapping("/dietitians/{dietitianId}/dishes")
    public ResponseEntity<List<DishResponseDto>> getDietitianAllDishes(@PathVariable UUID dietitianId) {
        List<DishResponseDto> dishResponseDtoList = dishService.getDietitianAllDishes(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @Operation(
            summary = "Create new dish"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dish created successfully",
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
                    description = "Dietitian not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DISH_CREATE)
    @PostMapping("/dishes")
    public ResponseEntity<DishResponseDto> createDish(@Valid @RequestBody CreateDishRequestDto createDishRequestDto) {
        DishResponseDto createdDishResponseDto = dishService.createDish(createDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/dishes/" + createdDishResponseDto.getDishId()))
                .body(createdDishResponseDto);
    }

    @Operation(
            summary = "Update dish by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dish updated successfully",
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
                    description = "Dish not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DISH_UPDATE)
    @PatchMapping("/dishes/{dishId}")
    public ResponseEntity<DishResponseDto> updateDishById(@PathVariable Long dishId, @Valid @RequestBody UpdateDishRequestDto updateDishRequestDto) {
        DishResponseDto dishResponseDto = dishService.updateDishById(dishId, updateDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDto);
    }

    @Operation(
            summary = "Delete dish by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Dish deleted successfully"
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
                    description = "Dish not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DISH_DELETE)
    @DeleteMapping("/dishes/{dishId}")
    public ResponseEntity<Void> deleteDishById(@PathVariable Long dishId) {
        dishService.deleteDishById(dishId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
