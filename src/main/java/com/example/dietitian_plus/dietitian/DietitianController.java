package com.example.dietitian_plus.dietitian;

import com.example.dietitian_plus.dietitian.dto.CreateDietitianRequestDto;
import com.example.dietitian_plus.dietitian.dto.DietitianResponseDto;
import com.example.dietitian_plus.dish.dto.DishResponseDto;
import com.example.dietitian_plus.patient.dto.PatientResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dietitians")
public class DietitianController {

    private final DietitianService dietitianService;

    @Autowired
    public DietitianController(DietitianService dietitianService) {
        this.dietitianService = dietitianService;
    }

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

    @PostMapping("/")
    public ResponseEntity<DietitianResponseDto> createDietitian(@RequestBody CreateDietitianRequestDto createDietitianRequestDto) {
        DietitianResponseDto createdDietitianResponseDto = dietitianService.createDietitian(createDietitianRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/dietitians/" + createdDietitianResponseDto.getDietitianId())).body(createdDietitianResponseDto);
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
