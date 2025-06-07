package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianResponseDto;
import com.example.dietitian_plus.domain.dietitian.dto.UpdateDietitianRequestDto;
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

    @GetMapping
    public ResponseEntity<List<DietitianResponseDto>> getDietitians() {
        List<DietitianResponseDto> dietitianResponseDtoList = dietitianService.getDietitians();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDtoList);
    }

    @GetMapping("/{dietitianId}")
    public ResponseEntity<DietitianResponseDto> getDietitianById(@PathVariable Long dietitianId) {
        DietitianResponseDto dietitianResponseDto = dietitianService.getDietitianById(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDto);
    }

    @PutMapping("/{dietitianId}")
    public ResponseEntity<DietitianResponseDto> updateDietitianById(@PathVariable Long dietitianId, @RequestBody UpdateDietitianRequestDto updateDietitianRequestDto) {
        DietitianResponseDto dietitianResponseDto = dietitianService.updateDietitianById(dietitianId, updateDietitianRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDto);
    }

    @DeleteMapping("/{dietitianId}")
    public ResponseEntity<MessageResponseDto> deleteDietitianById(@PathVariable Long dietitianId) {
        dietitianService.deleteDietitianById(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dietitian with id " + dietitianId + " delete successfully"));
    }

}
