package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.dish.dto.CreateDishRequestDto;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.dish.dto.UpdateDishRequestDto;
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

    @GetMapping
    public ResponseEntity<List<DishResponseDto>> getDishes() {
        List<DishResponseDto> dishResponseDtoList = dishService.getDishes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @GetMapping("/{dishId}")
    public ResponseEntity<DishResponseDto> getDishById(@PathVariable Long dishId) {
        DishResponseDto dishResponseDto = dishService.getDishById(dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDto);
    }

    @PostMapping
    public ResponseEntity<DishResponseDto> createDish(@RequestBody CreateDishRequestDto createDishRequestDto) {
        DishResponseDto createdDishResponseDto = dishService.createDish(createDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/dishes/" + createdDishResponseDto.getDishId()))
                .body(createdDishResponseDto);
    }

    @PutMapping("/{dishId}")
    public ResponseEntity<DishResponseDto> updateDishById(@PathVariable Long dishId, @RequestBody UpdateDishRequestDto updateDishRequestDto) {
        DishResponseDto dishResponseDto = dishService.updateDishById(dishId, updateDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDto);
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<MessageResponseDto> deleteDishById(@PathVariable Long dishId) {
        dishService.deleteDishById(dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dish with id " + dishId + " deleted successfully"));
    }

}
