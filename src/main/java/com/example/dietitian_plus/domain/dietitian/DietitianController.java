package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.domain.dietitian.dto.DietitianResponseDto;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.patient.dto.PatientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dietitians")
@RequiredArgsConstructor
public class DietitianController {

    private final DietitianService dietitianService;

    @GetMapping("/")
    public ResponseEntity<List<DietitianResponseDto>> getDietitians() {
        return ResponseEntity.ok(dietitianService.getDietitians());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietitianResponseDto> getDietitianById(@PathVariable Long id) {
        return ResponseEntity.ok(dietitianService.getDietitianById(id));
    }

    @GetMapping("/{id}/patients")
    public ResponseEntity<List<PatientResponseDto>> getDietitianPatients(@PathVariable Long id) {
        return ResponseEntity.ok(dietitianService.getDietitianPatients(id));
    }

    @GetMapping("/{id}/dishes")
    public ResponseEntity<List<DishResponseDto>> getDietitianDishes(@PathVariable Long id) {
        return ResponseEntity.ok(dietitianService.getDietitianDishes(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietitianResponseDto> updateDietitianById(@PathVariable Long id, @RequestBody DietitianResponseDto dietitianResponseDto) {
        return ResponseEntity.ok(dietitianService.updateDietitianById(id, dietitianResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDietitianById(@PathVariable Long id) {
        dietitianService.deleteDietitianById(id);
        return ResponseEntity.ok("Dietitian deleted successfully");
    }

}
