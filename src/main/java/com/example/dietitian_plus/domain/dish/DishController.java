package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.auth.access.annotation.AdminOnly;
import com.example.dietitian_plus.auth.access.annotation.DietitianAccess;
import com.example.dietitian_plus.auth.access.annotation.OwnerDietitianAccess;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @AdminOnly
    @GetMapping("/dishes")
    public ResponseEntity<List<DishResponseDto>> getAllDishes() {
        List<DishResponseDto> dishResponseDtoList = dishService.getAllDishes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    // TODO check ownership in access manager
    @GetMapping("/dishes/{dishId}")
    public ResponseEntity<DishResponseDto> getDishById(@PathVariable Long dishId) {
        DishResponseDto dishResponseDto = dishService.getDishById(dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDto);
    }

    @OwnerDietitianAccess
    @GetMapping("/dietitians/{dietitianId}/dishes")
    public ResponseEntity<List<DishResponseDto>> getDietitianAllDishes(@PathVariable Long dietitianId) {
        List<DishResponseDto> dishResponseDtoList = dishService.getDietitianAllDishes(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @DietitianAccess
    @PostMapping("/dishes")
    public ResponseEntity<DishResponseDto> createDish(@RequestBody CreateDishRequestDto createDishRequestDto) {
        DishResponseDto createdDishResponseDto = dishService.createDish(createDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/dishes/" + createdDishResponseDto.getDishId()))
                .body(createdDishResponseDto);
    }

    // TODO check ownership in access manager
    @DietitianAccess
    @PutMapping("/dishes/{dishId}")
    public ResponseEntity<DishResponseDto> updateDishById(@PathVariable Long dishId, @RequestBody UpdateDishRequestDto updateDishRequestDto) {
        DishResponseDto dishResponseDto = dishService.updateDishById(dishId, updateDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDto);
    }

    // TODO check ownership in access manager
    @DietitianAccess
    @DeleteMapping("/dishes/{dishId}")
    public ResponseEntity<MessageResponseDto> deleteDishById(@PathVariable Long dishId) {
        dishService.deleteDishById(dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dish with id " + dishId + " deleted successfully"));
    }

}
