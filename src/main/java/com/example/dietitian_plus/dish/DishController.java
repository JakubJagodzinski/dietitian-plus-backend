package com.example.dietitian_plus.dish;

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
    public ResponseEntity<List<DishDto>> getDishes() {
        return ResponseEntity.ok(dishService.getDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDto> getDishById(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    @PostMapping("/")
    public ResponseEntity<DishDto> createDish(@RequestBody CreateDishDto createDishDto) {
        DishDto createdDishDto = dishService.createDish(createDishDto);
        return ResponseEntity
                .created(URI.create("/api/dishes/" + createdDishDto.getDishId()))
                .body(createdDishDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishDto> updateDishById(@PathVariable Long id, @RequestBody UpdateDishDto updateDishDto) {
        return ResponseEntity.ok(dishService.updateDishById(id, updateDishDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDishById(@PathVariable Long id) {
        dishService.deleteDishById(id);
        return ResponseEntity.ok("Dish deleted successfully");
    }

}
