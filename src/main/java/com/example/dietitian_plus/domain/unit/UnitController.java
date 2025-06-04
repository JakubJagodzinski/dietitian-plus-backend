package com.example.dietitian_plus.domain.unit;

import com.example.dietitian_plus.domain.unit.dto.CreateUnitRequestDto;
import com.example.dietitian_plus.domain.unit.dto.UnitResponseDto;
import com.example.dietitian_plus.domain.unit.dto.UpdateUnitResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping("/")
    public ResponseEntity<List<UnitResponseDto>> getUnits() {
        return ResponseEntity.ok(unitService.getUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitResponseDto> getUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    @PostMapping("/")
    public ResponseEntity<UnitResponseDto> createUnit(@RequestBody CreateUnitRequestDto createUnitRequestDto) {
        UnitResponseDto createdUnitResponseDto = unitService.createUnit(createUnitRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/units/" + createdUnitResponseDto.getUnitId())).body(createdUnitResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitResponseDto> updateUnitById(@PathVariable Long id, @RequestBody UpdateUnitResponseDto updateUnitResponseDto) {
        return ResponseEntity.ok(unitService.updateUnitById(id, updateUnitResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnitById(@PathVariable Long id) {
        unitService.deleteUnitById(id);
        return ResponseEntity.ok("Unit deleted successfully");
    }

}
