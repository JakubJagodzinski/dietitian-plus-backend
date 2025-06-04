package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianResponseDto;
import com.example.dietitian_plus.domain.dietitian.dto.UpdateDietitianRequestDto;
import com.example.dietitian_plus.domain.dish.dto.DishResponseDto;
import com.example.dietitian_plus.domain.patient.dto.PatientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        List<DietitianResponseDto> dietitianResponseDtoList = dietitianService.getDietitians();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietitianResponseDto> getDietitianById(@PathVariable Long id) {
        DietitianResponseDto dietitianResponseDto = dietitianService.getDietitianById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDto);
    }

    @GetMapping("/{id}/patients")
    public ResponseEntity<List<PatientResponseDto>> getDietitianPatients(@PathVariable Long id) {
        List<PatientResponseDto> patientResponseDtoList = dietitianService.getDietitianPatients(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientResponseDtoList);
    }

    @GetMapping("/{id}/dishes")
    public ResponseEntity<List<DishResponseDto>> getDietitianDishes(@PathVariable Long id) {
        List<DishResponseDto> dishResponseDtoList = dietitianService.getDietitianDishes(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dishResponseDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietitianResponseDto> updateDietitianById(@PathVariable Long id, @RequestBody UpdateDietitianRequestDto updateDietitianRequestDto) {
        DietitianResponseDto dietitianResponseDto = dietitianService.updateDietitianById(id, updateDietitianRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteDietitianById(@PathVariable Long id) {
        dietitianService.deleteDietitianById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dietitian with id " + id + " delete successfully"));
    }

}
