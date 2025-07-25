package com.example.dietitian_plus.domain.dishesproducts;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.dto.ApiErrorResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.request.AddProductToDishRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.request.UpdateDishProductRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.response.DishProductResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.response.DishWithProductsResponseDto;
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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class DishProductController {

    private final DishProductService dishProductService;

    @Operation(
            summary = "Get dish with its products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dish with its products",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DishWithProductsResponseDto.class)
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
                    description = "Dish not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.DISH_PRODUCT_READ_ALL)
    @GetMapping("/dishes/{dishId}/products")
    public ResponseEntity<DishWithProductsResponseDto> getDishWithProducts(@PathVariable Long dishId) {
        DishWithProductsResponseDto dishWithProductsResponseDto = dishProductService.getDishWithProducts(dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishWithProductsResponseDto);
    }

    @Operation(
            summary = "Add product to dish"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product added to dish successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DishProductResponseDto.class)
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
                    description = "Dish / product / unit not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.DISH_PRODUCT_ADD)
    @PostMapping("/dishes/{dishId}/products")
    public ResponseEntity<DishProductResponseDto> addProductToDish(@PathVariable Long dishId, @Valid @RequestBody AddProductToDishRequestDto addProductToDishRequestDto) {
        DishProductResponseDto createdDishProductResponseDto = dishProductService.addProductToDish(dishId, addProductToDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/dishes-products/" + createdDishProductResponseDto.getDishProductId()))
                .body(createdDishProductResponseDto);
    }

    @Operation(
            summary = "Update dish product"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dish product updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DishProductResponseDto.class)
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
                    description = "Dish product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.DISH_PRODUCT_UPDATE)
    @PatchMapping("/dishes-products/{dishProductId}")
    public ResponseEntity<DishProductResponseDto> updateDishProductById(@PathVariable Long dishProductId, @Valid @RequestBody UpdateDishProductRequestDto updateDishProductRequestDto) {
        DishProductResponseDto updatedDishProductResponseDto = dishProductService.updateDishProductById(dishProductId, updateDishProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDishProductResponseDto);
    }

    @Operation(
            summary = "Remove product from dish"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Product removed from dish successfully"
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
                    description = "Dish product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.DISH_PRODUCT_REMOVE)
    @DeleteMapping("/dishes-products/{dishProductId}")
    public ResponseEntity<Void> removeProductFromDish(@PathVariable Long dishProductId) {
        dishProductService.removeProductFromDish(dishProductId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
