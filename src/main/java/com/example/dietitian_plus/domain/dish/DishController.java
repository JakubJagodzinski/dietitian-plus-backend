package com.example.dietitian_plus.domain.dish;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.dish.dto.CreateDishRequestDto;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.dish.dto.UpdateDishRequestDto;
import com.example.dietitian_plus.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @CheckPermission(Permission.DISH_READ_ALL)
    @GetMapping("/dishes")
    public ResponseEntity<List<DishResponseDto>> getAllDishes() {
        List<DishResponseDto> dishResponseDtoList = dishService.getAllDishes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @CheckPermission(Permission.DISH_READ)
    @GetMapping("/dishes/{dishId}")
    public ResponseEntity<DishResponseDto> getDishById(@PathVariable Long dishId) {
        DishResponseDto dishResponseDto = dishService.getDishById(dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDto);
    }

    @CheckPermission(Permission.DIETITIAN_DISH_READ_ALL)
    @GetMapping("/dietitians/{dietitianId}/dishes")
    public ResponseEntity<List<DishResponseDto>> getDietitianAllDishes(@PathVariable UUID dietitianId) {
        List<DishResponseDto> dishResponseDtoList = dishService.getDietitianAllDishes(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @CheckPermission(Permission.DISH_CREATE)
    @PostMapping("/dishes")
    public ResponseEntity<DishResponseDto> createDish(@RequestBody CreateDishRequestDto createDishRequestDto) {
        DishResponseDto createdDishResponseDto = dishService.createDish(createDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/dishes/" + createdDishResponseDto.getDishId()))
                .body(createdDishResponseDto);
    }

    @CheckPermission(Permission.DISH_UPDATE)
    @PatchMapping("/dishes/{dishId}")
    public ResponseEntity<DishResponseDto> updateDishById(@PathVariable Long dishId, @RequestBody UpdateDishRequestDto updateDishRequestDto) {
        DishResponseDto dishResponseDto = dishService.updateDishById(dishId, updateDishRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDto);
    }

    @CheckPermission(Permission.DISH_DELETE)
    @DeleteMapping("/dishes/{dishId}")
    public ResponseEntity<MessageResponseDto> deleteDishById(@PathVariable Long dishId) {
        dishService.deleteDishById(dishId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dish with id " + dishId + " deleted successfully"));
    }

}
