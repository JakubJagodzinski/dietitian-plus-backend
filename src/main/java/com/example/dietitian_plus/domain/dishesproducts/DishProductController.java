package com.example.dietitian_plus.domain.dishesproducts;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.CreateDishProductRequestDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.DishProductResponseDto;
import com.example.dietitian_plus.domain.dishesproducts.dto.UpdateDishProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes-products")
@RequiredArgsConstructor
public class DishProductController {

    private final DishProductService dishProductService;

    @GetMapping("/by-dish/{dishId}")
    public ResponseEntity<List<DishProductResponseDto>> getDishProductsByDishId(@PathVariable Long dishId) {
        List<DishProductResponseDto> dishProductResponseDtoList = dishProductService.getDishProductsByDishId(dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishProductResponseDtoList);
    }

    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<DishProductResponseDto>> getDishProductsByProductId(@PathVariable Long productId) {
        List<DishProductResponseDto> dishProductResponseDtoList = dishProductService.getDishProductsByProductId(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishProductResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<DishProductResponseDto> createDishProduct(@RequestBody CreateDishProductRequestDto createDishProductRequestDto) {
        DishProductResponseDto createdDishProductResponseDto = dishProductService.createDishProduct(createDishProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/dishes-products/" + createdDishProductResponseDto.getDishId() + "/" + createdDishProductResponseDto.getProductId()))
                .body(createdDishProductResponseDto);

    }

    @PutMapping("/{dishProductId}")
    public ResponseEntity<DishProductResponseDto> updateDishProductById(@PathVariable Long dishProductId, @RequestBody UpdateDishProductRequestDto updateDishProductRequestDto) {
        DishProductResponseDto updatedDishProductResponseDto = dishProductService.updateDishProductById(dishProductId, updateDishProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedDishProductResponseDto);
    }

    @DeleteMapping("/{dishProductId}")
    public ResponseEntity<MessageResponseDto> deleteDishProductById(@PathVariable Long dishProductId) {
        dishProductService.deleteDishProductById(dishProductId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dish product with id " + dishProductId + " deleted successfully"));
    }

}
