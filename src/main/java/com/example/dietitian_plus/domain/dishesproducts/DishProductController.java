package com.example.dietitian_plus.domain.dishesproducts;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.dishesproducts.dto.request.AddProductToDishRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.request.UpdateDishProductRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.response.DishProductResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.response.DishWithProductsResponseDto;
import com.example.dietitian_plus.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DishProductController {

    private final DishProductService dishProductService;

    @CheckPermission(Permission.DISH_PRODUCT_READ_ALL)
    @GetMapping("/dishes/{dishId}/products")
    public ResponseEntity<DishWithProductsResponseDto> getDishWithProducts(@PathVariable Long dishId) {
        DishWithProductsResponseDto dishWithProductsResponseDto = dishProductService.getDishWithProducts(dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishWithProductsResponseDto);
    }

    @CheckPermission(Permission.DISH_PRODUCT_ADD)
    @PostMapping("/dishes/{dishId}/products")
    public ResponseEntity<DishProductResponseDto> addProductToDish(@PathVariable Long dishId, @RequestBody AddProductToDishRequestDto addProductToDishRequestDto) {
        DishProductResponseDto createdDishProductResponseDto = dishProductService.addProductToDish(dishId, addProductToDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/dishes-products/" + createdDishProductResponseDto.getDishProductId()))
                .body(createdDishProductResponseDto);
    }

    @CheckPermission(Permission.DISH_PRODUCT_UPDATE)
    @PatchMapping("/dishes-products/{dishProductId}")
    public ResponseEntity<DishProductResponseDto> updateDishProductById(@PathVariable Long dishProductId, @RequestBody UpdateDishProductRequestDto updateDishProductRequestDto) {
        DishProductResponseDto updatedDishProductResponseDto = dishProductService.updateDishProductById(dishProductId, updateDishProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDishProductResponseDto);
    }

    @CheckPermission(Permission.DISH_PRODUCT_REMOVE)
    @DeleteMapping("/dishes-products/{dishProductId}")
    public ResponseEntity<Void> removeProductFromDish(@PathVariable Long dishProductId) {
        dishProductService.removeProductFromDish(dishProductId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
