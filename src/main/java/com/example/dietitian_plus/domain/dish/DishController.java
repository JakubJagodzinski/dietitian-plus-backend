package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.dish.dto.CreateDishRequestDto;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.dish.dto.UpdateDishRequestDto;
import com.example.dietitian_plus.domain.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping("/")
    public ResponseEntity<List<DishResponseDto>> getDishes() {
        List<DishResponseDto> dishResponseDtoList = dishService.getDishes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDto> getDishById(@PathVariable Long id) {
        DishResponseDto dishResponseDto = dishService.getDishById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDto);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponseDto>> getDishProducts(@PathVariable Long id) {
        List<ProductResponseDto> productResponseDtoList = dishService.getDishProducts(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @PostMapping("/")
    public ResponseEntity<DishResponseDto> createDish(@RequestBody CreateDishRequestDto createDishRequestDto) {
        DishResponseDto createdDishResponseDto = dishService.createDish(createDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/dishes/" + createdDishResponseDto.getDishId()))
                .body(createdDishResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishResponseDto> updateDishById(@PathVariable Long id, @RequestBody UpdateDishRequestDto updateDishRequestDto) {
        DishResponseDto dishResponseDto = dishService.updateDishById(id, updateDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteDishById(@PathVariable Long id) {
        dishService.deleteDishById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dish with id " + id + " deleted successfully"));
    }

}
