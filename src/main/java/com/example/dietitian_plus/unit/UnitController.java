package com.example.dietitian_plus.unit;

import com.example.dietitian_plus.unit.dto.CreateUnitRequestDto;
import com.example.dietitian_plus.unit.dto.UnitResponseDto;
import com.example.dietitian_plus.unit.dto.UpdateUnitResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/units")
public class UnitController {

    private final UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

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
