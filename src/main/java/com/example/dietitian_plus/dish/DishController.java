package com.example.dietitian_plus.dish;

import com.example.dietitian_plus.dish.dto.CreateDishRequestDto;
import com.example.dietitian_plus.dish.dto.DishResponseDto;
import com.example.dietitian_plus.dish.dto.UpdateDishRequestDto;
import com.example.dietitian_plus.product.dto.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/")
    public ResponseEntity<List<DishResponseDto>> getDishes() {
        return ResponseEntity.ok(dishService.getDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDto> getDishById(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponseDto>> getDishProducts(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.getDishProducts(id));
    }

    @PostMapping("/")
    public ResponseEntity<DishResponseDto> createDish(@RequestBody CreateDishRequestDto createDishRequestDto) {
        DishResponseDto createdDishResponseDto = dishService.createDish(createDishRequestDto);
        return ResponseEntity.created(URI.create("/api/dishes/" + createdDishResponseDto.getDishId())).body(createdDishResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishResponseDto> updateDishById(@PathVariable Long id, @RequestBody UpdateDishRequestDto updateDishRequestDto) {
        return ResponseEntity.ok(dishService.updateDishById(id, updateDishRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDishById(@PathVariable Long id) {
        dishService.deleteDishById(id);
        return ResponseEntity.ok("Dish deleted successfully");
    }

}
