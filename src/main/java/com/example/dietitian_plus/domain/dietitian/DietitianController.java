package com.example.dietitian_plus.domain.dietitian;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.dietitian.dto.request.UpdateDietitianRequestDto;
import com.example.dietitian_plus.domain.dietitian.dto.response.DietitianResponseDto;
import com.example.dietitian_plus.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<DietitianResponseDto> getDietitianById(@PathVariable UUID dietitianId) {
        DietitianResponseDto dietitianResponseDto = dietitianService.getDietitianById(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDto);
    }

    @CheckPermission(Permission.DIETITIAN_UPDATE)
    @PatchMapping("/dietitians/{dietitianId}")
    public ResponseEntity<DietitianResponseDto> updateDietitianById(@PathVariable UUID dietitianId, @RequestBody UpdateDietitianRequestDto updateDietitianRequestDto) {
        DietitianResponseDto dietitianResponseDto = dietitianService.updateDietitianById(dietitianId, updateDietitianRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dietitianResponseDto);
    }

    @CheckPermission(Permission.DIETITIAN_DELETE)
    @DeleteMapping("/dietitians/{dietitianId}")
    public ResponseEntity<Void> deleteDietitianById(@PathVariable UUID dietitianId) {
        dietitianService.deleteDietitianById(dietitianId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
