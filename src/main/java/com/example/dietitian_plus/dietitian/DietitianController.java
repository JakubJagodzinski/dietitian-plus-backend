package com.example.dietitian_plus.dietitian;

import com.example.dietitian_plus.dish.DishDto;
import com.example.dietitian_plus.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/dietitians")
public class DietitianController {

    private final DietitianService dietitianService;

    @Autowired
    public DietitianController(DietitianService dietitianService) {
        this.dietitianService = dietitianService;
    }

    @GetMapping("/")
    public ResponseEntity<List<DietitianDto>> getDietitians() {
        return ResponseEntity.ok(dietitianService.getDietitians());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietitianDto> getDietitianById(@PathVariable Long id) {
        return ResponseEntity.ok(dietitianService.getDietitianById(id));
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<UserDto>> getDietitianUsers(@PathVariable Long id) {
        return ResponseEntity.ok(dietitianService.getDietitianUsers(id));
    }

    @GetMapping("/{id}/dishes")
    public ResponseEntity<List<DishDto>> getDietitianDishes(@PathVariable Long id) {
        return ResponseEntity.ok(dietitianService.getDietitianDishes(id));
    }

    @PostMapping("/")
    public ResponseEntity<DietitianDto> createDietitian(@RequestBody CreateDietitianDto createDietitianDto) {
        DietitianDto createdDietitianDto = dietitianService.createDietitian(createDietitianDto);
        return ResponseEntity
                .created(URI.create("/api/dietitians/" + createdDietitianDto.getDietitianId()))
                .body(createdDietitianDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietitianDto> updateDietitianById(@PathVariable Long id, @RequestBody DietitianDto dietitianDto) {
        return ResponseEntity.ok(dietitianService.updateDietitianById(id, dietitianDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDietitianById(@PathVariable Long id) {
        dietitianService.deleteDietitianById(id);
        return ResponseEntity.ok("Dietitian deleted successfully");
    }

}
