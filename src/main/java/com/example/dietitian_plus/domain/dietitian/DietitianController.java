package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.dietitian.dto.DietitianResponseDto;
import com.example.dietitian_plus.domain.dietitian.dto.UpdateDietitianRequestDto;
import com.example.dietitian_plus.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DietitianController {

    private final DietitianService dietitianService;

    @CheckPermission(Permission.DIETITIAN_READ_ALL)
    @GetMapping("/dietitians")
    public ResponseEntity<List<DietitianResponseDto>> getAllDietitians() {
        List<DietitianResponseDto> dietitianResponseDtoList = dietitianService.getAllDietitians();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDtoList);
    }

    @CheckPermission(Permission.DIETITIAN_READ)
    @GetMapping("/dietitians/{dietitianId}")
    public ResponseEntity<DietitianResponseDto> getDietitianById(@PathVariable Long dietitianId) {
        DietitianResponseDto dietitianResponseDto = dietitianService.getDietitianById(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDto);
    }

    @CheckPermission(Permission.DIETITIAN_UPDATE)
    @PutMapping("/dietitians/{dietitianId}")
    public ResponseEntity<DietitianResponseDto> updateDietitianById(@PathVariable Long dietitianId, @RequestBody UpdateDietitianRequestDto updateDietitianRequestDto) {
        DietitianResponseDto dietitianResponseDto = dietitianService.updateDietitianById(dietitianId, updateDietitianRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDto);
    }

    @CheckPermission(Permission.DIETITIAN_DELETE)
    @DeleteMapping("/dietitians/{dietitianId}")
    public ResponseEntity<MessageResponseDto> deleteDietitianById(@PathVariable Long dietitianId) {
        dietitianService.deleteDietitianById(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Dietitian with id " + dietitianId + " delete successfully"));
    }

}
