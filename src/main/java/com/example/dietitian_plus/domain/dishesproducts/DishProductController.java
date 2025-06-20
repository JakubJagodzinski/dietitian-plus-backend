package com.example.dietitian_plus.domain.dishesproducts;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.CreateDishProductEntryRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.DishProductResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.DishWithProductsResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.UpdateDishProductEntryRequestDto;
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

    @CheckPermission(Permission.DISH_PRODUCT_ASSIGN)
    @PostMapping("/dishes-products")
    public ResponseEntity<DishProductResponseDto> createDishProductEntry(@RequestBody CreateDishProductEntryRequestDto createDishProductEntryRequestDto) {
        DishProductResponseDto createdDishProductResponseDto = dishProductService.createDishProductEntry(createDishProductEntryRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/dishes-products/" + createdDishProductResponseDto.getDishProductId()))
                .body(createdDishProductResponseDto);
    }

    @CheckPermission(Permission.DISH_PRODUCT_UPDATE)
    @PatchMapping("/dishes-products/{dishProductId}")
    public ResponseEntity<DishProductResponseDto> updateDishProductEntryById(@PathVariable Long dishProductId, @RequestBody UpdateDishProductEntryRequestDto updateDishProductEntryRequestDto) {
        DishProductResponseDto updatedDishProductResponseDto = dishProductService.updateDishProductEntryById(dishProductId, updateDishProductEntryRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDishProductResponseDto);
    }

    @CheckPermission(Permission.DISH_PRODUCT_UNASSIGN)
    @DeleteMapping("/dishes-products/{dishProductId}")
    public ResponseEntity<MessageResponseDto> deleteDishProductEntryById(@PathVariable Long dishProductId) {
        dishProductService.deleteDishProductEntryById(dishProductId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dish product entry with id " + dishProductId + " deleted successfully"));
    }

}
